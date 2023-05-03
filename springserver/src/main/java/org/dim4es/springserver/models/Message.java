package org.dim4es.springserver.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "message")
public class Message extends AbstractEntity {

    @Column(name = "date")
    private LocalDateTime date;

    @Column(name = "info")
    private String info;

    @ManyToOne
    @JoinColumn(name = "chat_id", referencedColumnName = "id")
    @JsonIgnore         
    private Chat chatOwner;

    public Message() {
    }

    public Message(LocalDateTime date, String info) {
        this.date = date;
        this.info = info;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Chat getChatOwner() {
        return chatOwner;
    }

    public void setChatOwner(Chat chatOwner) {
        this.chatOwner = chatOwner;
    }
}
