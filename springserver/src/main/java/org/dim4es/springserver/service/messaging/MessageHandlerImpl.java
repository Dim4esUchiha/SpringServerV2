package org.dim4es.springserver.service.messaging;

import org.dim4es.springserver.dto.messaging.AckMessage;
import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.MessageReadMarkDto;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.model.chat.ChatType;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.repository.jpa.ChatRepository;
import org.dim4es.springserver.service.chat.ChatService;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.messaging.forward.PrivateChatMessageForwarder;
import org.dim4es.springserver.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import static org.dim4es.springserver.service.Constants.DESTINATION_PREFIX_USERS;

@Service
public class MessageHandlerImpl implements MessageHandler {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerImpl.class);

    private final MessageService messageService;
    private final UserService userService;
    private final ChatRepository chatRepository;
    private final ChatService chatService;
    private final PrivateChatMessageForwarder privateChatMessageForwarder;

    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public MessageHandlerImpl(MessageService messageService,
                              UserService userService,
                              ChatRepository chatRepository,
                              ChatService chatService,
                              PrivateChatMessageForwarder privateChatMessageForwarder,
                              SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.chatService = chatService;
        this.privateChatMessageForwarder = privateChatMessageForwarder;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handleIncomingMessage(IncomingMessageDto messageDto) {
        try {
            User fromUser = userService.findUserById(messageDto.getFromUserId());
            Chat chat = chatRepository.findById(messageDto.getChatId()).orElseThrow(() ->
                    new EntityNotFoundException("Unable to find chat with id = " + messageDto.getChatId()));

            Message message = messageService.saveMessage(fromUser, chat, messageDto);

            sendMessageAck(fromUser, message);

            if (chat.getType() == ChatType.PRIVATE) {
                privateChatMessageForwarder.forwardMessage(message);
            } else if (chat.getType() == ChatType.GROUP) {
                // not implemented yet
            }

        } catch (EntityNotFoundException e) {
            logger.error("Unable to process incoming message", e);
        }
    }

    @Override
    public void handleUserStatusChange(String username, UserStatus newStatus) {
        try {
            User user = userService.findByUsername(username).orElseThrow(() ->
                    new EntityNotFoundException("Unable to find User by username = " + username));

            userService.updateUserStatus(user, newStatus);
            privateChatMessageForwarder.forwardUserStatusChange(user);
        } catch (EntityNotFoundException e) {
            logger.error("Unable to process user status change", e);
        }
    }

    @Override
    public void handleMessageRead(MessageReadMarkDto dto) {
        try {
            UserPrivateChat userPrivateChat = chatService.updateLastReadMessage(
                    dto.getFromUserId(), dto.getChatId(), dto.getLastReadMessageId());

            privateChatMessageForwarder.forwardReadMessageMark(userPrivateChat);
        } catch (EntityNotFoundException e) {
            logger.error("Unable to process read message notification", e);
        }
    }

    private void sendMessageAck(User toUser, Message message) {
        long timestamp = message.getTimestamp().toEpochMilli();
        AckMessage ack = new AckMessage(message.getChat().getId(), timestamp, message.getId());

        messagingTemplate.convertAndSend(DESTINATION_PREFIX_USERS + toUser.getUsername(), ack);
    }
}
