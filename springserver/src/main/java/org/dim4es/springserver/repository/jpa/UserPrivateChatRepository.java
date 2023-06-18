package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserPrivateChatRepository extends JpaRepository<UserPrivateChat, Long> {

    @Query("select upc from UserPrivateChat upc where upc.user.id = :userId")
    List<UserPrivateChat> findAllUserChats(Long userId);

    Optional<UserPrivateChat> findByUserIdAndChatId(Long userId, Long chatId);

    Optional<UserPrivateChat> findByAnotherUserIdAndChatId(Long anotherUserId, Long chatId);
}
