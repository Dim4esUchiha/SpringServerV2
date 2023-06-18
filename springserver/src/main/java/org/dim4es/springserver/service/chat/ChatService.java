package org.dim4es.springserver.service.chat;

import org.dim4es.springserver.dto.messaging.ChatDto;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.exception.UnprocessableEntityException;

import java.util.List;

public interface ChatService {

    void createPrivateChat(Long userId, Long anotherUserId) throws EntityNotFoundException, UnprocessableEntityException;

    List<ChatDto> getUserChats(Long userId);

    UserPrivateChat updateLastReadMessage(Long userId, Long chatId, Long lastReadMessageId) throws EntityNotFoundException;

}
