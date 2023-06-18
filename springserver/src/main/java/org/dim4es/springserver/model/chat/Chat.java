package org.dim4es.springserver.model.chat;

import org.dim4es.springserver.model.AbstractEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.Table;

@Entity
@Table(name = "chat")
public class Chat extends AbstractEntity {

    @Column(name = "name")
    private String name;

    @Enumerated
    private ChatType type;

    public Chat() {
    }

    public Chat(String name, ChatType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ChatType getType() {
        return type;
    }

    public void setType(ChatType type) {
        this.type = type;
    }
}
