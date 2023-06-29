package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.messaging.ChatDto;
import org.dim4es.springserver.dto.messaging.ChatMessagesInfo;
import org.dim4es.springserver.dto.messaging.CreatePrivateChatDto;
import org.dim4es.springserver.dto.messaging.PrivateChatMessageDto;
import org.dim4es.springserver.service.chat.ChatService;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.exception.UnprocessableEntityException;
import org.dim4es.springserver.service.messaging.MessageService;
import org.dim4es.springserver.web.security.UserInfoDetails;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
public class ChatController {

    private final ChatService chatService;
    private final MessageService messageService;

    public ChatController(ChatService chatService, MessageService messageService) {
        this.chatService = chatService;
        this.messageService = messageService;
    }

    @PostMapping("/createPrivateChat")
    public ResponseEntity<Void> createPrivateChat(@AuthenticationPrincipal UserInfoDetails userDetails,
                                                  @RequestBody CreatePrivateChatDto createChatDto)
            throws EntityNotFoundException, UnprocessableEntityException {

        chatService.createPrivateChat(userDetails.getUser().getId(), createChatDto.getUserId());
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<ChatDto>> getAllUserChats(@AuthenticationPrincipal UserInfoDetails userDetails) {
        List<ChatDto> chats = chatService.getUserChats(userDetails.getUser().getId());
        return ResponseEntity.ok(chats);
    }

    @GetMapping("/{chatId}/messages")
    public ResponseEntity<List<PrivateChatMessageDto>> getPrivateChatMessages(@PathVariable Long chatId,
                                                                              @AuthenticationPrincipal UserInfoDetails userDetails,
                                                                              @RequestParam(required = false) Long timestamp,
                                                                              Pageable pageable) {
        Long userId = userDetails.getUser().getId();
        List<PrivateChatMessageDto> privateChatMessages = messageService.getMessagesAfterTimestamp(
                userId, chatId, timestamp, pageable);
        return ResponseEntity.ok(privateChatMessages);
    }

    @GetMapping("/{chatId}/info")
    public ResponseEntity<ChatMessagesInfo> countPrivateChatMessages(@PathVariable Long chatId,
                                                                     @AuthenticationPrincipal UserInfoDetails userDetails,
                                                                     @RequestParam(required = false) Long timestamp)
            throws EntityNotFoundException {
        ChatMessagesInfo info = messageService.getChatMessagesInfo(chatId, userDetails.getUser().getId(), timestamp);
        return ResponseEntity.ok(info);
    }
}
