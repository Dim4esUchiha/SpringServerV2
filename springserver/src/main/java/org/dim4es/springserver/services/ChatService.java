package org.dim4es.springserver.services;

import org.dim4es.springserver.models.Chat;
import org.dim4es.springserver.repositories.ChatRepository;
import org.dim4es.springserver.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;

    @Autowired
    public ChatService(ChatRepository chatRepository, UserRepository userRepository) {
        this.chatRepository = chatRepository;
        this.userRepository = userRepository;
    }

    public List<Chat> getAllUserChats(int id){
        return userRepository.findById(id).get().getChats();
    }
}
