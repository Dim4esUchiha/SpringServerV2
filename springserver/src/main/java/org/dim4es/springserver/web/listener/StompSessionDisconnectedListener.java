package org.dim4es.springserver.web.listener;

import org.dim4es.springserver.model.UserStatus;
import org.dim4es.springserver.service.messaging.MessageHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.security.Principal;

@Component
public class StompSessionDisconnectedListener implements ApplicationListener<SessionDisconnectEvent> {

    private final MessageHandler messageHandler;

    @Autowired
    public StompSessionDisconnectedListener(MessageHandler messageHandler) {
        this.messageHandler = messageHandler;
    }

    @Override
    public void onApplicationEvent(SessionDisconnectEvent event) {
        Principal user = event.getUser();
        if (user != null) {
            messageHandler.handleUserStatusChange(user.getName(), UserStatus.OFFLINE);
        }
    }
}
