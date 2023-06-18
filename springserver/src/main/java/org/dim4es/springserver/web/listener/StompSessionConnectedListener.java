package org.dim4es.springserver.web.listener;

import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.service.messaging.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectedEvent;

import java.security.Principal;

@Component
public class StompSessionConnectedListener implements ApplicationListener<SessionConnectedEvent> {

    private final MessageHandler messageHandler;

    @Autowired
    public StompSessionConnectedListener(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onApplicationEvent(SessionConnectedEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            messageHandler.handleUserStatusChange(user.getName(), UserStatus.ONLINE);
        }
    }
}
