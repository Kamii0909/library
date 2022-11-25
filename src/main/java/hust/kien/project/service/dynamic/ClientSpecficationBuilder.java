package hust.kien.project.service.dynamic;

import hust.kien.project.model.client.Client;

public class ClientSpecficationBuilder extends GeneralLibrarySpecificationBuilder<Client> {

    @Override
    public Class<Client> libraryType() {
        return Client.class;
    }

    @Override
    public ClientCollectionInitBuilder initCollection() {
        return new ClientCollectionInitBuilder();
    }

    public class ClientCollectionInitBuilder extends LibraryCollectionInitBuilder<Client> {

        @Override
        public ClientSpecficationBuilder back() {
            return ClientSpecficationBuilder.this;
        }
    }

}
