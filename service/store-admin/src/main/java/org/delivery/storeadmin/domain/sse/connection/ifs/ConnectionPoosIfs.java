package org.delivery.storeadmin.domain.sse.connection.ifs;

import org.delivery.storeadmin.domain.sse.connection.model.UserSseConnection;

public interface ConnectionPoosIfs<T, R> {

    void addSession(T key, R session);
    //UserSseConnection getSession(String uniqueKey);
    R getSession(T uniqueKey);

    void onCompletionCallback(R session);
}
