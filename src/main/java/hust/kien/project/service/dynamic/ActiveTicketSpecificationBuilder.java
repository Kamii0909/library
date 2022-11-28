package hust.kien.project.service.dynamic;

import hust.kien.project.model.rent.Ticket_;
import hust.kien.project.model.ticket.ActiveTicket;

public class ActiveTicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<ActiveTicket> {

    @Override
    public Class<ActiveTicket> libraryType() {
        return ActiveTicket.class;
    }

    @Override
    public ActiveTicketCollectionInitBuilder initCollection() {
        return new ActiveTicketCollectionInitBuilder();
    }

    public class ActiveTicketCollectionInitBuilder extends LibraryCollectionInitBuilder<ActiveTicket> {
        @Override
        public ActiveTicketSpecificationBuilder back() {
            return ActiveTicketSpecificationBuilder.this;
        }
    }

    @Override
    public ActiveTicketSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.id), id));
        return this;
    }
}
