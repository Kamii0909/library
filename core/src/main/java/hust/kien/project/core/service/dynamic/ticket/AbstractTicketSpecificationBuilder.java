package hust.kien.project.core.service.dynamic.ticket;

import java.time.LocalDate;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.book.BookInfo_;
import hust.kien.project.core.model.book.Book_;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.client.ClientContactInfo_;
import hust.kien.project.core.model.client.Client_;
import hust.kien.project.core.model.ticket.Ticket;
import hust.kien.project.core.model.ticket.Ticket_;
import hust.kien.project.core.service.dynamic.GeneralLibrarySpecificationBuilder;
import hust.kien.project.core.service.dynamic.LibraryCollectionInitBuilder;

public abstract class AbstractTicketSpecificationBuilder<T extends Ticket> extends GeneralLibrarySpecificationBuilder<T> {

    public AbstractTicketSpecificationBuilder<T> startDateBetween(LocalDate from, LocalDate to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Ticket_.startDate), from, to));
        return this;
    }

    public AbstractTicketSpecificationBuilder<T> book(Book book) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.book), book));
        return this;
    }

    public AbstractTicketSpecificationBuilder<T> bookNameLike(String name) {
        specList.add((root, cq, cb) -> cb
            .like(root.get(Ticket_.book).get(Book_.bookInfo).get(BookInfo_.name),
                "%" + name + "%"));
        return this;
    }

    public AbstractTicketSpecificationBuilder<T> client(Client client) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.client), client));
        return this;
    }

    public AbstractTicketSpecificationBuilder<T> clientNameLike(String name) {
        specList.add((root, cq, cb) -> cb
            .like(root.get(Ticket_.client).get(Client_.contactInfo).get(ClientContactInfo_.name),
                "%" + name + "%"));
        return this;
    }

    public AbstractTicketSpecificationBuilder<T> init() {
        specList.add((root, cq, fb) -> {
            root.fetch(Ticket_.book);
            root.fetch(Ticket_.client);
            return null;
        });
        return this;
    }

    /**
     * @deprecated
     */
    @Override
    @Deprecated
    public TicketCollectionInitBuilder initCollection() {
        return new TicketCollectionInitBuilder();
    }

    public class TicketCollectionInitBuilder extends LibraryCollectionInitBuilder<T> {
        @Override
        public AbstractTicketSpecificationBuilder<T> back() {
            return AbstractTicketSpecificationBuilder.this;
        }
    }

    @Override
    public AbstractTicketSpecificationBuilder<T> withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Ticket_.id), id));
        return this;
    }
}
