package hust.kien.project.service;

import java.time.LocalDate;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.ActiveTicket;
import hust.kien.project.model.rent.ClosedTicket;
import hust.kien.project.model.rent.Ticket;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;

@Service
@Transactional
public class LibraryBorrowServiceImpl implements LibraryBorrowService {

    @Autowired
    private LibraryMetadataService metadataService;

    @Override
    public Ticket createActiveTicket(Book book, Client client) {

        book.getBookStock().setStock(book.getBookStock().getStock() - 1);
        metadataService.saveOrUpdate(book);

        Ticket newTicket = ActiveTicket.builder()
            .book(book)
            .client(client)
            .startDate(LocalDate.now())
            .build();

        return metadataService.saveOrUpdate(newTicket);
    }

    @Override
    public Ticket createClosedTicket(Book book, Client client, LocalDate startDate,
        LocalDate endDate) {
        Ticket ticket = ClosedTicket.builder()
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
    public Ticket createActiveTicket(Book book, Client client, LocalDate startDate) {
        if (!Hibernate.isInitialized(book)) {
            book = metadataService
                .dynamicFind(new BookSpecificationBuilder().withId(book.getId()))
                .get(0);
        }

        book.getBookStock().setStock(book.getBookStock().getStock() - 1);
        metadataService.saveOrUpdate(book);

        Ticket newTicket = ActiveTicket.builder()
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
