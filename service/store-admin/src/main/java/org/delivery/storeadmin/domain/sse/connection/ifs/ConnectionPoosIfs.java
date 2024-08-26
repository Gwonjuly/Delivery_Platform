package org.delivery.storeadmin.domain.sse.connection.ifs;


public interface ConnectionPoosIfs<T, R> {

    void addSession(T key, R session);
    R getSession(T uniqueKey);

    void onCompletionCallback(R session);
}
