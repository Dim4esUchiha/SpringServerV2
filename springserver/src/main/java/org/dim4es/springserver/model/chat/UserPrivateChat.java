package org.dim4es.springserver.model.chat;

import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class UserPrivateChat extends AbstractUserChat {

    @Column(nullable = false)
    private String name;

    @ManyToOne(optional = false)
    @JoinColumn(name = "another_user_id")
    private User anotherUser;

    @ManyToOne
    @JoinColumn(name = "last_read_message_id")
    private Message lastReadMessage;

    public UserPrivateChat() {
    }

    public UserPrivateChat(Chat chat, User user, User anotherUser) {
        super(chat, user);
        this.anotherUser = anotherUser;
        this.name = anotherUser.getUsername();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getAnotherUser() {
        return anotherUser;
    }

    public void setAnotherUser(User anotherUser) {
        this.anotherUser = anotherUser;
    }

    public Message getLastReadMessage() {
        return lastReadMessage;
    }

    public void setLastReadMessage(Message lastReadMessage) {
        this.lastReadMessage = lastReadMessage;
    }
}
