package org.dim4es.springserver.service.messaging;

import org.dim4es.springserver.dto.messaging.ChatMessagesInfo;
import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.PrivateChatMessageDto;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.repository.criteria.MessageRepository;
import org.dim4es.springserver.repository.jpa.UserPrivateChatRepository;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class MessageServiceImpl implements MessageService {

    private final MessageRepository messageRepository;
    private final UserPrivateChatRepository userPrivateChatRepository;

    @Autowired
    public MessageServiceImpl(MessageRepository messageRepository, UserPrivateChatRepository userPrivateChatRepository) {
        this.messageRepository = messageRepository;
        this.userPrivateChatRepository = userPrivateChatRepository;
    }

    @Override
    public Message saveMessage(User fromUser, Chat chat, IncomingMessageDto messageDto) {
        Instant timestamp = Instant.ofEpochMilli(messageDto.getTimestamp());
        Message newMessage = new Message(chat, fromUser, timestamp, messageDto.getContent());

        return messageRepository.save(newMessage);
    }

    @Override
    public List<PrivateChatMessageDto> getMessagesAfterTimestamp(Long userId, long chatId, Long timestamp, Pageable pageable) {
        Instant timestampParam = timestamp != null ? Instant.ofEpochMilli(timestamp) : null;
        List<Message> messages = messageRepository.getByChatIdAndAfterTimestamp(chatId, timestampParam, pageable);

        Function<Message, PrivateChatMessageDto> mapper = message -> {
            PrivateChatMessageDto messageDto = new PrivateChatMessageDto();
            messageDto.setId(message.getId());
            messageDto.setTimestamp(message.getTimestamp().toEpochMilli());
            messageDto.setContent(message.getContent());
            boolean isIncoming = !Objects.equals(message.getFromUser().getId(), userId);
            messageDto.setIsIncoming(isIncoming);
            return messageDto;
        };
        return messages.stream().map(mapper).collect(Collectors.toList());
    }

    @Override
    public ChatMessagesInfo getChatMessagesInfo(long chatId, long userId, Long timestamp) throws EntityNotFoundException {
        Instant timestampParam = timestamp != null ? Instant.ofEpochMilli(timestamp) : null;
        long numberOfMessages = messageRepository.countByChatIdAndAfterTimestamp(chatId, timestampParam);

        UserPrivateChat theirPrivateChat = userPrivateChatRepository.findByAnotherUserIdAndChatId(userId, chatId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find UserPrivateChat with anotherUserId=" + userId
                        + " and chatId=" + chatId));
        UserPrivateChat ourPrivateChat = userPrivateChatRepository.findByUserIdAndChatId(userId, chatId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find UserPrivateChat with userId=" + userId
                        + " and chatId=" + chatId));
        Instant lastReadMessageTime = null;
        if (ourPrivateChat.getLastReadMessage() != null) {
            lastReadMessageTime = ourPrivateChat.getLastReadMessage().getTimestamp();
        }
        long numberOfUnreadMessages = messageRepository.countByChatIdAndUserIdAndAfterTimestamp(chatId,
                theirPrivateChat.getUser().getId(), lastReadMessageTime);

        ChatMessagesInfo info = new ChatMessagesInfo();
        info.setNumberOfMessages(numberOfMessages);
        info.setNumberOfUnreadMessages(numberOfUnreadMessages);
        Message ourMessage = theirPrivateChat.getLastReadMessage();
        Message theirMessage = ourPrivateChat.getLastReadMessage();
        if (ourMessage != null) {
            info.setOurMessageLastReadTime(ourMessage.getTimestamp().toEpochMilli());
        }
        if (theirMessage != null && numberOfUnreadMessages > 0) {
            info.setTheirMessageLastReadTime(theirMessage.getTimestamp().toEpochMilli());
        }
        return info;
    }
}
