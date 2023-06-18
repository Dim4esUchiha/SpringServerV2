package org.dim4es.springserver.service.messaging.forward;

import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.UserPrivateChat;

public interface MessageForwarder {

    void forwardMessage(Message message);

    void forwardUserStatusChange(User user);

    void forwardReadMessageMark(UserPrivateChat userPrivateChat);
}
