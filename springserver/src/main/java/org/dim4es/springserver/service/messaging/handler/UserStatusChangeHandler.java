package org.dim4es.springserver.service.messaging.handler;

import org.dim4es.springserver.model.User;
import org.dim4es.springserver.service.exception.EntityNotFoundException;
import org.dim4es.springserver.service.messaging.forward.PrivateChatForwarder;
import org.dim4es.springserver.service.user.UserService;
import org.dim4es.springserver.web.listener.UserStatusChange;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class UserStatusChangeHandler implements MessageHandler<UserStatusChange> {

    private static final Logger logger = LoggerFactory.getLogger(UserStatusChangeHandler.class);

    private final UserService userService;
    private final PrivateChatForwarder privateChatForwarder;

    public UserStatusChangeHandler(UserService userService,
                                   PrivateChatForwarder privateChatForwarder) {
        this.userService = userService;
        this.privateChatForwarder = privateChatForwarder;
    }

    @Override
    public void handle(UserStatusChange data) {
        try {
            User user = userService.findByUsername(data.getUsername()).orElseThrow(() ->
                    new EntityNotFoundException("Unable to find User by username = " + data.getUsername()));

            userService.updateUserStatus(user, data.getStatus());
            privateChatForwarder.forwardUserStatusChange(user);
        } catch (EntityNotFoundException e) {
            logger.error("Unable to process user status change", e);
        }
    }
}
