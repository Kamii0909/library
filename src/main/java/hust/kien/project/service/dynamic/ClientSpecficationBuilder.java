package hust.kien.project.service.dynamic;

import hust.kien.project.model.client.Client;

public class ClientSpecficationBuilder extends GeneralLibrarySpecificationBuilder<Client> {

    @Override
    public GeneralLibrarySpecificationBuilder<Client> initCollection() {
        specList.add((root, cq, cb) -> {

            return null;
        });
        return this;
    }

    @Override
    public Class<Client> libraryType() {
        return Client.class;
    }

}
