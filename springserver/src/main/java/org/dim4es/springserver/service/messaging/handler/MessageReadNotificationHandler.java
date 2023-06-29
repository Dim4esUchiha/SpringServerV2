package org.dim4es.springserver.service.messaging.handler;

import org.dim4es.springserver.dto.messaging.MessageReadMarkDto;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.service.chat.ChatService;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.messaging.forward.PrivateChatForwarder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MessageReadNotificationHandler implements MessageHandler<MessageReadMarkDto> {

    private static final Logger logger = LoggerFactory.getLogger(MessageReadNotificationHandler.class);

    private final ChatService chatService;
    private final PrivateChatForwarder privateChatForwarder;

    @Autowired
    public MessageReadNotificationHandler(ChatService chatService,
                                          PrivateChatForwarder privateChatForwarder) {
        this.chatService = chatService;
        this.privateChatForwarder = privateChatForwarder;
    }

    @Override
    public void handle(MessageReadMarkDto data) {
        try {
            UserPrivateChat userPrivateChat = chatService.updateLastReadMessage(data.getFromUserId(),
                    data.getChatId(), data.getLastReadMessageId());

            privateChatForwarder.forwardReadMessageMark(userPrivateChat);
        } catch (EntityNotFoundException e) {
            logger.error("Unable to process read message notification", e);
        }
    }
}
