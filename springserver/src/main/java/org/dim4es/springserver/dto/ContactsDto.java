package org.dim4es.springserver.dto;

import org.dim4es.springserver.models.User;

import java.util.List;

public class ContactsDto {

    private Long chatId;
    private String chatName;
    List<UserDto> usersWithoutAuthenticatedUser;

    public ContactsDto () {

    }

    public ContactsDto(Long chatId, String chatName, List<UserDto> usersWithoutAuthenticatedUser) {
        this.chatId = chatId;
        this.chatName = chatName;
        this.usersWithoutAuthenticatedUser = usersWithoutAuthenticatedUser;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<UserDto> getUsersWithoutAuthenticatedUser() {
        return usersWithoutAuthenticatedUser;
    }

    public void setUsersWithoutAuthenticatedUser(List<UserDto> usersWithoutAuthenticatedUser) {
        this.usersWithoutAuthenticatedUser = usersWithoutAuthenticatedUser;
    }
}
