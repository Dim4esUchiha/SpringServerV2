package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.MessageReadMarkDto;
import org.dim4es.springserver.service.messaging.handler.IncomingMessageHandler;
import org.dim4es.springserver.service.messaging.handler.MessageReadNotificationHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final IncomingMessageHandler incomingMessageHandler;
    private final MessageReadNotificationHandler messageReadHandler;

    @Autowired
    public MessageController(IncomingMessageHandler incomingMessageHandler,
                             MessageReadNotificationHandler messageReadHandler) {
        this.incomingMessageHandler = incomingMessageHandler;
        this.messageReadHandler = messageReadHandler;
    }

    @MessageMapping("/messages")
    public void handleIncomingMessage(IncomingMessageDto messageDto) {
        incomingMessageHandler.handle(messageDto);
    }

    @MessageMapping("/messages/read")
    public void handleMessageRead(MessageReadMarkDto messageReadDto) {
        messageReadHandler.handle(messageReadDto);
    }
}
