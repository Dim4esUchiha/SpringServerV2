package org.dim4es.springserver.service.messaging;

import org.dim4es.springserver.dto.messaging.ChatMessagesInfo;
import org.dim4es.springserver.dto.messaging.IncomingMessageDto;
import org.dim4es.springserver.dto.messaging.PrivateChatMessageDto;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface MessageService {

    Message saveMessage(User fromUser, Chat chat, IncomingMessageDto messageDto);

    List<PrivateChatMessageDto> getMessagesAfterTimestamp(Long userId, long chatId, Long timestamp, Pageable pageable);

    ChatMessagesInfo getChatMessagesInfo(long chatId, long userId, Long timestamp) throws EntityNotFoundException;

}
