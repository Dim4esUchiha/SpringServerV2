package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.MessageReadMarkDto;
import org.dim4es.springserver.service.messaging.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    private final MessageHandler messageHandler;

    @Autowired
    public MessageController(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @MessageMapping("/messages")
    public void handleIncomingMessage(IncomingMessageDto messageDto) {
        messageHandler.handleIncomingMessage(messageDto);
    }

    @MessageMapping("/messages/read")
    public void handleMessageRead(MessageReadMarkDto messageReadDto) {
        messageHandler.handleMessageRead(messageReadDto);
    }
}
