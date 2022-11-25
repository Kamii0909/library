package hust.kien.project.service.dynamic;

import hust.kien.project.model.rent.Ticket;
import hust.kien.project.model.rent.Ticket_;

public class TicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<Ticket> {

    @Override
    public Class<Ticket> libraryType() {
        return Ticket.class;
    }

    @Override
    public TicketCollectionInitBuilder initCollection() {
        return new TicketCollectionInitBuilder();
    }

    public class TicketCollectionInitBuilder extends LibraryCollectionInitBuilder<Ticket> {
        @Override
        public TicketSpecificationBuilder back() {
            return TicketSpecificationBuilder.this;
        }
    }

    @Override
    public TicketSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.id), id));
        return this;
    }
}
