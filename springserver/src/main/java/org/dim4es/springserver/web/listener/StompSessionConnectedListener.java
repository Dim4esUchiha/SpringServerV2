package org.dim4es.springserver.web.listener;

import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.service.messaging.handler.UserStatusChangeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

@Component
public class StompSessionConnectedListener implements ApplicationListener<SessionConnectedEvent> {

    private final UserStatusChangeHandler userStatusChangeHandler;

    @Autowired
    public StompSessionConnectedListener(UserStatusChangeHandler handler) {
        userStatusChangeHandler = handler;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            userStatusChangeHandler.handle(new UserStatusChange(user.getName(), UserStatus.ONLINE));
        }
    }
}
