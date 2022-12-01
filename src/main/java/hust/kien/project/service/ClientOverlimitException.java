package hust.kien.project.service;

import hust.kien.project.model.client.Client;

public class ClientOverlimitException extends RuntimeException {

    private Client client;

    public ClientOverlimitException(Client client) {
        super("A client with " + client.getRentInfo().getClientTier()
            + " can only borrow a maximum of "
            + client.getRentInfo().getClientTier().getMaximumCanBorrow()
            + " at the same time");
            
        this.client = client;
    }

    public Client getClient() {
        return client;
    }

}
