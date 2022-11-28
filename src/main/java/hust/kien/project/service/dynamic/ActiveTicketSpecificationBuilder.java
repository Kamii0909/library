package hust.kien.project.service.dynamic;

import java.time.LocalDate;
import ch.qos.logback.core.net.server.Client;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.rent.Ticket_;
import hust.kien.project.model.ticket.ActiveTicket;

public class ActiveTicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<ActiveTicket> {

    public ActiveTicketSpecificationBuilder startDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Ticket_.startDate), from, to));
        return this;
    }
    
    public ActiveTicketSpecificationBuilder book(Book book) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.book), book));
        return this;
    }

    public ActiveTicketSpecificationBuilder client(Client client) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.client), client));
        return this;
    }

    @Override
    public Class<ActiveTicket> libraryType() {
        return ActiveTicket.class;
    }

    @Override
    @Deprecated
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
