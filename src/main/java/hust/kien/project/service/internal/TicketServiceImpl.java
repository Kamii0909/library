package hust.kien.project.service.internal;

import java.time.LocalDate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.Ticket;
import hust.kien.project.service.ClientOverlimitException;
import hust.kien.project.service.OutOfStockException;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import hust.kien.project.service.dynamic.ClientSpecficationBuilder;

@Service
public class TicketServiceImpl implements TicketService {

    @Autowired
    private LibraryMetadataService metadataService;

    @Override
    public ActiveTicket createActiveTicket(Book book, Client client) {

        if (!Hibernate.isInitialized(book)) {
            book = metadataService
                .dynamicFind(new BookSpecificationBuilder().withId(book.getId()))
                .get(0);
        }

        // The number of book in stock
        int i = book.getBookStock().getStock();

        if (i > 0) {
            book.getBookStock().setStock(i - 1);
        } else
            throw new OutOfStockException(book);

        if (!Hibernate.isInitialized(client.getRentInfo().getActiveTickets())) {
            client = metadataService
                .dynamicFind(new ClientSpecficationBuilder()
                    .withId(client.getId())
                    .initCollection()
                    .activeTickets().back())
                .get(0);
        }

        // The number of books a client is holding
        int j = client.getRentInfo().getActiveTickets().size();

        if (j >= client.getRentInfo().getClientTier().getMaximumCanBorrow()) {
            throw new ClientOverlimitException(client);
        }


        book = metadataService.saveOrUpdate(book);

        ActiveTicket newTicket = ActiveTicket.builder()
            .book(book)
            .client(client)
            .startDate(LocalDate.now())
            .build();


        return metadataService.saveOrUpdate(newTicket);
    }

    @Override
    public ClosedTicket createClosedTicket(Book book, Client client, LocalDate startDate,
        LocalDate endDate) {
        ClosedTicket ticket = ClosedTicket.builder()
            .book(book)
            .client(client)
            .startDate(startDate)
            .endDate(endDate).build();

        return metadataService.saveOrUpdate(ticket);
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        metadataService.delete(ticket);
    }

    @Override
    public ActiveTicket createActiveTicket(Book book, Client client, LocalDate startDate) {
        if (!Hibernate.isInitialized(book)) {
            book = metadataService
                .dynamicFind(new BookSpecificationBuilder().withId(book.getId()))
                .get(0);
        }

        book.getBookStock().setStock(book.getBookStock().getStock() - 1);
        metadataService.saveOrUpdate(book);

        ActiveTicket newTicket = ActiveTicket.builder()
            .book(book)
            .client(client)
            .startDate(startDate)
            .build();

        return metadataService.saveOrUpdate(newTicket);
    }

    @Override
    public ClosedTicket closeActiveTicket(ActiveTicket ticket) {
        Book book = ticket.getBook();
        Client client = ticket.getClient();
        LocalDate startDate = ticket.getStartDate();

        if (!Hibernate.isInitialized(book)) {
            book = metadataService
                .dynamicFind(new BookSpecificationBuilder().withId(book.getId()))
                .get(0);
        }
        book.getBookStock().setStock(book.getBookStock().getStock() + 1);

        metadataService.saveOrUpdate(book);

        ClosedTicket closedTicket = ClosedTicket.builder()
            .book(book)
            .client(client)
            .startDate(startDate)
            .endDate(LocalDate.now())
            .build();

        deleteTicket(ticket);
        return metadataService.saveOrUpdate(closedTicket);
    }
}
