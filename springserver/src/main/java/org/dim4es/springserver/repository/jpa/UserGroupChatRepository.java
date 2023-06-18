package org.dim4es.springserver.repository.jpa;

import org.dim4es.springserver.model.chat.UserGroupChat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserGroupChatRepository extends JpaRepository<UserGroupChat, Long> {
}
