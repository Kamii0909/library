package hust.kien.project.core.service.dynamic;

import java.time.LocalDate;
import java.util.Collection;

import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.client.ClientContactInfo_;
import hust.kien.project.core.model.client.ClientRentInfo_;
import hust.kien.project.core.model.client.ClientTier;
import hust.kien.project.core.model.client.Client_;
import hust.kien.project.core.model.client.Subscription_;
import jakarta.persistence.criteria.JoinType;

public class ClientSpecficationBuilder extends GeneralLibrarySpecificationBuilder<Client> {

    @Override
    public ClientSpecficationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Client_.id), id));
        return this;
    }

    @Override
    public Class<Client> libraryType() {
        return Client.class;
    }

    public ClientSpecficationBuilder isActive() {
        specList.add((root, cq, cb) -> cb.greaterThanOrEqualTo(
            root.get(Client_.rentInfo).get(ClientRentInfo_.subscription).get(Subscription_.endDate),
            LocalDate.now()));
        return this;
    }

    public ClientSpecficationBuilder withTierIn(Collection<ClientTier> tiers) {
        specList.add(
            (root, cq, cb) -> root.get(Client_.rentInfo).get(ClientRentInfo_.clientTier).in(tiers));
        return this;
    }

    public ClientSpecficationBuilder nameLike(String name) {
        specList.add((root, cq, cb) -> cb
            .like(root.get(Client_.contactInfo).get(ClientContactInfo_.name), "%" + name + "%"));
        return this;
    }

    public ClientSpecficationBuilder addressLike(String address) {
        specList.add((root, cq, cb) -> cb
            .like(root.get(Client_.contactInfo).get(ClientContactInfo_.address), "%" + address + "%"));
        return this;
    }


    @Override
    public ClientCollectionInitBuilder initCollection() {
        return new ClientCollectionInitBuilder();
    }

    public class ClientCollectionInitBuilder extends LibraryCollectionInitBuilder<Client> {

        public ClientCollectionInitBuilder activeTickets() {
            specList.add((root, cq, cb) -> {
                root.fetch(Client_.rentInfo).fetch(ClientRentInfo_.activeTickets, JoinType.LEFT);
                return null;
            });
            return this;
        }

        public ClientCollectionInitBuilder closedTickets() {
            specList.add((root, cq, cb) -> {
                root.fetch(Client_.rentInfo).fetch(ClientRentInfo_.closedTickets, JoinType.LEFT);
                return null;
            });
            return this;
        }

        @Override
        public ClientSpecficationBuilder back() {
            return ClientSpecficationBuilder.this;
        }
    }



}
