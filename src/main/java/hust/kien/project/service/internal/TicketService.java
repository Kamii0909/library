package hust.kien.project.service.internal;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.Ticket;

public interface TicketService {

    ActiveTicket createActiveTicket(Book book, Client client);

    ActiveTicket createActiveTicket(Book book, Client client, LocalDate startDate);

    ClosedTicket createClosedTicket(Book book, Client client, LocalDate startDate, LocalDate endDate);

    ClosedTicket closeActiveTicket(ActiveTicket ticket);

    void deleteTicket(Ticket ticket);
}
