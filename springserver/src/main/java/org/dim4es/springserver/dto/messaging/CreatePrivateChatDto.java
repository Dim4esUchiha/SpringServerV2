package org.dim4es.springserver.dto.messaging;

public class CreatePrivateChatDto {

    private Long userId;

    public CreatePrivateChatDto() {
    }

    public CreatePrivateChatDto(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
