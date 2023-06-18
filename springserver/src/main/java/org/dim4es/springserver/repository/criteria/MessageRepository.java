package org.dim4es.springserver.repository.criteria;

import org.dim4es.springserver.model.Message;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

public interface MessageRepository {

    Message save(Message message);

    Optional<Message> findById(Long id);

    List<Message> getByChatIdAndAfterTimestamp(Long chatId, Instant timestamp, Pageable pageable);

    long countByChatIdAndAfterTimestamp(Long chatId, Instant timestamp);

    long countByChatIdAndUserIdAndAfterTimestamp(Long chatId, Long fromUserId, Instant timestamp);

    Optional<Message> findLastChatMessage(Long chatId);
}
