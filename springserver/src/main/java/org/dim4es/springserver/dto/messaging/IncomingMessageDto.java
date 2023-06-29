package org.dim4es.springserver.dto.messaging;

public class IncomingMessageDto extends AbstractMessageToHandle {

    private long chatId;
    private long fromUserId;
    private long timestamp;
    private String content;

    public IncomingMessageDto() {
    }

    public IncomingMessageDto(Long chatId, long fromUserId, long timestamp, String content) {
        this.chatId = chatId;
        this.fromUserId = fromUserId;
        this.timestamp = timestamp;
        this.content = content;
    }

    public long getChatId() {
        return chatId;
    }

    public void setChatId(long chatId) {
        this.chatId = chatId;
    }

    public long getFromUserId() {
        return fromUserId;
    }

    public void setFromUserId(long fromUserId) {
        this.fromUserId = fromUserId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
