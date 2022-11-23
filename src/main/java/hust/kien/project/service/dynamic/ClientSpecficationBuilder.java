package hust.kien.project.service.dynamic;

import hust.kien.project.model.client.Client;

public class ClientSpecficationBuilder extends GeneralLibrarySpecificationBuilder<Client> {

    @Override
    public GeneralLibrarySpecificationBuilder<Client> setInitCollections(boolean initCollections) {
        doSetInitCollections(initCollections);
        return this;
    }

}
