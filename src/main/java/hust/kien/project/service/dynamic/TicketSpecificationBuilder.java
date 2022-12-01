package hust.kien.project.service.dynamic;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.Ticket_;
import hust.kien.project.model.ticket.Ticket;

public class TicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<Ticket> {

    public TicketSpecificationBuilder startDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Ticket_.startDate), from, to));
        return this;
    }

    public TicketSpecificationBuilder book(Book book) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.book), book));
        return this;
    }

    public TicketSpecificationBuilder client(Client client) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.client), client));
        return this;
    }

    @Override
    public Class<Ticket> libraryType() {
        return Ticket.class;
    }

    @Override
    @Deprecated
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
