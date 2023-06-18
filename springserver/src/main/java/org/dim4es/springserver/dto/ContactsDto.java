package org.dim4es.springserver.dto;

import java.util.List;

public class ContactsDto {

    private Long chatId;
    private String chatName;
    List<UserNearbyDto> usersWithoutAuthenticatedUser;

    public ContactsDto () {

    }

    public ContactsDto(Long chatId, String chatName, List<UserNearbyDto> usersWithoutAuthenticatedUser) {
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

    public List<UserNearbyDto> getUsersWithoutAuthenticatedUser() {
        return usersWithoutAuthenticatedUser;
    }

    public void setUsersWithoutAuthenticatedUser(List<UserNearbyDto> usersWithoutAuthenticatedUser) {
        this.usersWithoutAuthenticatedUser = usersWithoutAuthenticatedUser;
    }
}
