package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.models.Chat;
import org.dim4es.springserver.services.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/chat")
public class ChatController {
    private final ChatService chatService;

    @Autowired
    public ChatController(ChatService chatService) {
        this.chatService = chatService;
    }


    @GetMapping(path = "/{id}")
    public List<Chat> getAllChats(@PathVariable("id") Long id){
        return chatService.getAllUserChats(id);
    }
}
