package org.dim4es.springserver.dto.messaging;

public class AckMessage extends AbstractChatMessage {

    private long timestamp;
    private long messageId;

    public AckMessage() {
    }

    public AckMessage(Long chatId, long timestamp, long messageId) {
        super(chatId, MessageType.MESSAGE_ACK);
        this.timestamp = timestamp;
        this.messageId = messageId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }
}
