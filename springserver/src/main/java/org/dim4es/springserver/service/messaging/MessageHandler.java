package org.dim4es.springserver.service.messaging;

import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.MessageReadMarkDto;
import org.dim4es.springserver.model.UserStatus;

public interface MessageHandler {

    void handleIncomingMessage(IncomingMessageDto messageDto);

    void handleUserStatusChange(String username, UserStatus newStatus);

    void handleMessageRead(MessageReadMarkDto readMark);
}
