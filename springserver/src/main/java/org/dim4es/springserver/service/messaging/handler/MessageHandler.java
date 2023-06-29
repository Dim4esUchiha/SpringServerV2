package org.dim4es.springserver.service.messaging.handler;

import org.dim4es.springserver.dto.messaging.AbstractMessageToHandle;

public interface MessageHandler<T extends AbstractMessageToHandle> {

    void handle(T data);
}
