package hust.kien.project.service.dynamic;

import hust.kien.project.model.rent.Ticket_;
import hust.kien.project.model.ticket.ClosedTicket;

public class ClosedTicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<ClosedTicket> {

    @Override
    public Class<ClosedTicket> libraryType() {
        return ClosedTicket.class;
    }

    @Override
    public ClosedTicketCollectionInitBuilder initCollection() {
        return new ClosedTicketCollectionInitBuilder();
    }

    public class ClosedTicketCollectionInitBuilder extends LibraryCollectionInitBuilder<ClosedTicket> {
        @Override
        public ClosedTicketSpecificationBuilder back() {
            return ClosedTicketSpecificationBuilder.this;
        }
    }

    @Override
    public ClosedTicketSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.id), id));
        return this;
    }
}
