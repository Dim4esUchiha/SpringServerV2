package org.dim4es.springserver.web.listener;

import org.dim4es.springserver.dto.messaging.AbstractMessageToHandle;
import org.dim4es.springserver.model.UserStatus;

public class UserStatusChange extends AbstractMessageToHandle {

    private final String username;
    private final UserStatus status;

    public UserStatusChange(String username, UserStatus status) {
        this.username = username;
        this.status = status;
    }

    public String getUsername() {
        return username;
    }

    public UserStatus getStatus() {
        return status;
    }
}
