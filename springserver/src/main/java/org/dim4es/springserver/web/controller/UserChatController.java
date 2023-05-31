package org.dim4es.springserver.web.controller;

import org.dim4es.springserver.dto.ContactsDto;
import org.dim4es.springserver.dto.UserDto;
import org.dim4es.springserver.models.Chat;
import org.dim4es.springserver.models.User;
import org.dim4es.springserver.security.UserInfoDetails;
import org.dim4es.springserver.services.ChatService;
import org.dim4es.springserver.services.UserService;
import org.dim4es.springserver.services.exception.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "api/userChat")
public class UserChatController {

    private final UserService userService;
    private final ChatService chatService;

    @Autowired
    public UserChatController(UserService userService, ChatService chatService) {
        this.userService = userService;
        this.chatService = chatService;
    }

    @PostMapping(path = "/createChatForTwoUsers")
    public ResponseEntity<Void> createChat(@AuthenticationPrincipal UserInfoDetails userDetails,
                                               @RequestParam Long secondUserId) throws EntityNotFoundException {

        for ( Chat chat: userDetails.getUser().getChats()) {
            for (User user: chat.getUsers()) {
                if (user.getId().equals(secondUserId)) {
                    return ResponseEntity.status(409).build();
                }
            }
        }
        chatService.createChat(userDetails.getUser(), userService.getUserById(secondUserId));
        return ResponseEntity.ok().build();
    }

    @GetMapping(path="/getChatsForUser")
    public ResponseEntity<List<ContactsDto>> getChatsForUser(@AuthenticationPrincipal UserInfoDetails userDetails) {
        User user = userDetails.getUser();
        List<ContactsDto> contactsDtoList = new ArrayList<>();
        List<Chat> chatList = user.getChats();
        for (Chat chat : chatList) {
            ContactsDto contactsDto = new ContactsDto();
            contactsDto.setChatName(chat.getChatName());
            contactsDto.setChatId(chat.getId());
            List<User> users = chat.getUsers();
            List<UserDto> userDtos = new ArrayList<>();
            for ( User tempUser : users) {
                if (!Objects.equals(tempUser.getId(), user.getId())){
                    UserDto userDto = new UserDto(tempUser.getId(), tempUser.getNickname(), null);
                    userDtos.add(userDto);
                }
            }
            contactsDto.setUsersWithoutAuthenticatedUser(userDtos);
            contactsDtoList.add(contactsDto);
        }
        return ResponseEntity.ok(contactsDtoList);
    }


}
