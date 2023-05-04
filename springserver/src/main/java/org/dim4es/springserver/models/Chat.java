package org.dim4es.springserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "chat")
public class Chat extends AbstractEntity {

    @Column(name = "chat_name")
    private String chatName;

    @OneToMany(mappedBy = "chatOwner")
    private List<Message> messages;

    @ManyToMany(mappedBy = "chats")
    @JsonIgnore
    private List<User> users;

    public Chat() {
    }

    public Chat(String chatName) {
        this.chatName = chatName;
    }

    public String getChatName() {
        return chatName;
    }

    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
