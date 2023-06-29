package org.dim4es.springserver.web.listener;

import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.service.messaging.handler.UserStatusChangeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class StompSessionDisconnectedListener implements ApplicationListener<SessionDisconnectEvent> {

    private final UserStatusChangeHandler userStatusChangeHandler;

    @Autowired
    public StompSessionDisconnectedListener(UserStatusChangeHandler handler) {
        userStatusChangeHandler = handler;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            userStatusChangeHandler.handle(new UserStatusChange(user.getName(), UserStatus.OFFLINE));
        }
    }
}
