package org.dim4es.springserver.model;

import org.dim4es.springserver.model.chat.Chat;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;

    @ManyToOne
    @JoinColumn(name = "from_user_id")
    private User fromUser;

    @Column(nullable = false)
    private Instant timestamp;

    @Column(nullable = false)
    private String content;

    public Message() {
    }

    public Message(Chat chat, User fromUser, Instant timestamp, String content) {
        this.chat = chat;
        this.fromUser = fromUser;
        this.timestamp = timestamp;
        this.content = content;
    }

    public Chat getChat() {
        return chat;
    }

    public void setChat(Chat chat) {
        this.chat = chat;
    }

    public User getFromUser() {
        return fromUser;
    }

    public void setFromUser(User fromUser) {
        this.fromUser = fromUser;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
