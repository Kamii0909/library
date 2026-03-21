package hust.kien.project.core.service;

import hust.kien.project.core.client.CurrentSubscription;
import hust.kien.project.core.client.ReadonlyClient;

public class ClientOverlimitException extends RuntimeException {

    private final transient ReadonlyClient client;

    public ClientOverlimitException(ReadonlyClient client, CurrentSubscription subscription) {
        super("A client with " + subscription.tier()
            + " can only borrow a maximum of "
            + subscription.tier().maximumCanBorrow()
            + " at the same time");

        this.client = client;
    }

    public ReadonlyClient getClient() {
        return client;
    }

}
