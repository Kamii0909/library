package hust.kien.project.service.dynamic;

import java.time.LocalDate;
import ch.qos.logback.core.net.server.Client;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.ticket.Ticket_;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.ClosedTicket_;

public class ClosedTicketSpecificationBuilder extends
                                              GeneralLibrarySpecificationBuilder<ClosedTicket> {

    public ClosedTicketSpecificationBuilder startDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Ticket_.startDate), from, to));
        return this;
    }

    public ClosedTicketSpecificationBuilder endDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(ClosedTicket_.endDate), from, to));
        return this;
    }

    public ClosedTicketSpecificationBuilder book(Book book) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.book), book));
        return this;
    }

    public ClosedTicketSpecificationBuilder client(Client client) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.client), client));
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
                                                   LibraryCollectionInitBuilder<ClosedTicket> {
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
