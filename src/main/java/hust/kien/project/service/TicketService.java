package hust.kien.project.service;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.ActiveTicket;
import hust.kien.project.model.rent.ClosedTicket;
import hust.kien.project.model.rent.Ticket;

public interface TicketService {

    ActiveTicket createActiveTicket(Book book, Client client);

    @Deprecated
    ActiveTicket createActiveTicket(Book book, Client client, LocalDate startDate);

    @Deprecated
    ClosedTicket createClosedTicket(Book book, Client client, LocalDate startDate, LocalDate endDate);

    ClosedTicket closeActiveTicket(ActiveTicket ticket);

    void deleteTicket(Ticket ticket);
}
