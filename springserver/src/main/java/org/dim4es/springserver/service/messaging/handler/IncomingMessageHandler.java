package org.dim4es.springserver.service.messaging.handler;

import org.dim4es.springserver.dto.messaging.AckMessage;
import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.model.chat.ChatType;
import org.dim4es.springserver.repository.jpa.ChatRepository;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.messaging.MessageService;
import org.dim4es.springserver.service.messaging.forward.PrivateChatForwarder;
import org.dim4es.springserver.service.user.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import static org.dim4es.springserver.service.Constants.DESTINATION_PREFIX_USERS;

@Component
public class IncomingMessageHandler implements MessageHandler<IncomingMessageDto> {

    private static final Logger logger = LoggerFactory.getLogger(IncomingMessageHandler.class);

    private final UserService userService;
    private final ChatRepository chatRepository;
    private final MessageService messageService;

    private final PrivateChatForwarder privateChatForwarder;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public IncomingMessageHandler(PrivateChatForwarder privateChatForwarder,
                                  UserService userService,
                                  ChatRepository chatRepository,
                                  MessageService messageService,
                                  SimpMessagingTemplate messagingTemplate) {
        this.privateChatForwarder = privateChatForwarder;
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void handle(IncomingMessageDto data) {
        try {
            User fromUser = userService.findUserById(data.getFromUserId());
            Chat chat = chatRepository.findById(data.getChatId()).orElseThrow(() ->
                    new EntityNotFoundException("Unable to find chat with id = " + data.getChatId()));

            Message message = messageService.saveMessage(fromUser, chat, data);
            sendMessageAck(fromUser, message);

            if (chat.getType() == ChatType.PRIVATE) {
                privateChatForwarder.forwardMessage(message);
            }
        } catch (EntityNotFoundException e) {
            logger.error("Unable to process incoming message", e);
        }
    }

    private void sendMessageAck(User toUser, Message message) {
        long timestamp = message.getTimestamp().toEpochMilli();
        AckMessage ack = new AckMessage(message.getChat().getId(), timestamp, message.getId());
        messagingTemplate.convertAndSend(DESTINATION_PREFIX_USERS + toUser.getUsername(), ack);
    }
}
