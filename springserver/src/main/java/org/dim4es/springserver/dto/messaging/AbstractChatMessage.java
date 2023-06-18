package org.dim4es.springserver.dto.messaging;

public abstract class AbstractChatMessage {

    private Long chatId;
    private MessageType type;

    public AbstractChatMessage() {
    }

    public AbstractChatMessage(Long chatId, MessageType type) {
        this.chatId = chatId;
        this.type = type;
    }

    public Long getChatId() {
        return chatId;
    }

    public void setChatId(Long chatId) {
        this.chatId = chatId;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }
}
