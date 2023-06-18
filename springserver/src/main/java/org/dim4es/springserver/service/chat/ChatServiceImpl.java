package org.dim4es.springserver.service.chat;

import org.dim4es.springserver.dto.messaging.ChatDto;
import org.dim4es.springserver.dto.messaging.UserStatusDto;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.model.chat.ChatType;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.repository.criteria.MessageRepository;
import org.dim4es.springserver.repository.jpa.ChatRepository;
import org.dim4es.springserver.repository.jpa.UserPrivateChatRepository;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.exception.UnprocessableEntityException;
import org.dim4es.springserver.service.user.UserService;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ChatServiceImpl implements ChatService {

    private final UserService userService;
    private final ChatRepository chatRepository;
    private final UserPrivateChatRepository userPrivateChatRepository;
    private final MessageRepository messageRepository;

    public ChatServiceImpl(UserService userService,
                           ChatRepository chatRepository,
                           UserPrivateChatRepository userPrivateChatRepository,
                           MessageRepository messageRepository) {
        this.userService = userService;
        this.chatRepository = chatRepository;
        this.userPrivateChatRepository = userPrivateChatRepository;
        this.messageRepository = messageRepository;
    }

    @Transactional
    @Override
    public void createPrivateChat(Long userId, Long anotherUserId) throws EntityNotFoundException, UnprocessableEntityException {
        List<UserPrivateChat> userChats = userPrivateChatRepository.findAllUserChats(userId);
        boolean hasChatWithAnotherUser = userChats.stream()
                .anyMatch(chat -> Objects.equals(chat.getAnotherUser().getId(), anotherUserId));
        if (hasChatWithAnotherUser) {
            throw new UnprocessableEntityException("User{id=" + userId + "} already has chat with User{id="
                    + anotherUserId + "}");
        }

        User user = userService.findUserById(userId);
        User anotherUser = userService.findUserById(anotherUserId);

        Chat newChat = new Chat();
        newChat.setType(ChatType.PRIVATE);
        chatRepository.save(newChat);

        List<UserPrivateChat> chatsToSave = new ArrayList<>();
        chatsToSave.add(new UserPrivateChat(newChat, user, anotherUser));
        chatsToSave.add(new UserPrivateChat(newChat, anotherUser, user));
        userPrivateChatRepository.saveAll(chatsToSave);
    }

    @Override
    public List<ChatDto> getUserChats(Long userId) {
        List<UserPrivateChat> privateChats = userPrivateChatRepository.findAllUserChats(userId);

        Function<User, UserStatusDto> userStatusMapper = user -> new UserStatusDto(user.getId(), user.getStatus());

        Function<UserPrivateChat, ChatDto> chatMapper = userPrivateChat -> {
            ChatDto chatDto = new ChatDto();
            chatDto.setId(userPrivateChat.getChat().getId());
            chatDto.setName(userPrivateChat.getName());

            UserStatusDto userStatusDto = userStatusMapper.apply(userPrivateChat.getAnotherUser());
            chatDto.setUserStatus(userStatusDto);
            return chatDto;
        };
        return privateChats.stream()
                .map(chatMapper)
                .collect(Collectors.toList());
    }

    @Override
    public UserPrivateChat updateLastReadMessage(Long userId, Long chatId, Long lastReadMessageId) throws EntityNotFoundException {
        UserPrivateChat userPrivateChat = userPrivateChatRepository.findByUserIdAndChatId(userId, chatId)
                .orElseThrow(() -> new EntityNotFoundException("Unable to find UserPrivateChat with userId=" + userId +
                                " and chatId=" + chatId));

        Message lastReadMessage = messageRepository.findById(lastReadMessageId).orElseThrow(
                () -> new EntityNotFoundException("Unable to find Message by id=" + lastReadMessageId));
        userPrivateChat.setLastReadMessage(lastReadMessage);
        userPrivateChatRepository.save(userPrivateChat);
        return userPrivateChat;
    }
}
