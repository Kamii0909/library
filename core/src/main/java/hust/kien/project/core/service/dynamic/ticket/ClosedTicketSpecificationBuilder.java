package hust.kien.project.core.service.dynamic.ticket;

import java.time.LocalDate;

import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.model.ticket.ClosedTicket_;
import hust.kien.project.core.model.ticket.Ticket_;

public class ClosedTicketSpecificationBuilder extends AbstractTicketSpecificationBuilder<ClosedTicket> {

    public ClosedTicketSpecificationBuilder endDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(ClosedTicket_.endDate), from, to));
        return this;
    }

    @Override
    public Class<ClosedTicket> libraryType() {
        return ClosedTicket.class;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public ClosedTicketCollectionInitBuilder initCollection() {
        return new ClosedTicketCollectionInitBuilder();
    }

    public class ClosedTicketCollectionInitBuilder extends
        TicketCollectionInitBuilder {
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
