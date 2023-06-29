package org.dim4es.springserver.service.messaging.forward;

import org.dim4es.springserver.dto.messaging.MessageRead;
import org.dim4es.springserver.dto.messaging.NewMessage;
import org.dim4es.springserver.dto.messaging.UserStatusChangeMessage;
import org.dim4es.springserver.model.Message;
import org.dim4es.springserver.model.User;
import org.dim4es.springserver.model.chat.Chat;
import org.dim4es.springserver.model.chat.UserPrivateChat;
import org.dim4es.springserver.repository.jpa.UserPrivateChatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static org.dim4es.springserver.service.Constants.DESTINATION_PREFIX_USERS;

@Component
public class PrivateChatForwarder implements MessageForwarder {

    private final UserPrivateChatRepository userPrivateChatRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @Autowired
    public PrivateChatForwarder(UserPrivateChatRepository userPrivateChatRepository,
                                SimpMessagingTemplate messagingTemplate) {
        this.userPrivateChatRepository = userPrivateChatRepository;
        this.messagingTemplate = messagingTemplate;
    }

    @Override
    public void forwardMessage(Message message) {
        Chat chat = message.getChat();
        User fromUser = message.getFromUser();
        Optional<UserPrivateChat> userPrivateChatOpt = userPrivateChatRepository.findByUserIdAndChatId(
                fromUser.getId(), chat.getId());

        if (userPrivateChatOpt.isEmpty()) {
            throw new IllegalStateException("Private chat record must exist for Chat{id=" + chat.getId()
                    + "} and User{id=" + fromUser.getId() + "}");
        }
        UserPrivateChat userPrivateChat = userPrivateChatOpt.get();

        long timestamp = message.getTimestamp().toEpochMilli();
        NewMessage newMessage = new NewMessage(chat.getId(), message.getId(), timestamp,
                fromUser.getId(), message.getContent());

        User toUser = userPrivateChat.getAnotherUser();
        messagingTemplate.convertAndSend(DESTINATION_PREFIX_USERS + toUser.getUsername(), newMessage);
    }

    @Override
    public void forwardUserStatusChange(User user) {
        List<UserPrivateChat> userPrivateChats = userPrivateChatRepository.findAllUserChats(user.getId());

        for (UserPrivateChat userPrivateChat : userPrivateChats) {
            UserStatusChangeMessage statusChangeMessage = new UserStatusChangeMessage(
                    userPrivateChat.getChat().getId(), user.getId(), user.getStatus());

            User toUser = userPrivateChat.getAnotherUser();
            messagingTemplate.convertAndSend(DESTINATION_PREFIX_USERS + toUser.getUsername(), statusChangeMessage);
        }
    }

    @Override
    public void forwardReadMessageMark(UserPrivateChat userPrivateChat) {
        MessageRead messageReadNotice = new MessageRead(userPrivateChat.getChat().getId(),
                userPrivateChat.getLastReadMessage().getTimestamp().toEpochMilli());

        User toUser = userPrivateChat.getAnotherUser();
        messagingTemplate.convertAndSend(DESTINATION_PREFIX_USERS + toUser.getUsername(), messageReadNotice);
    }
}
