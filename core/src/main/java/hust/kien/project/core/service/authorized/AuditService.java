package hust.kien.project.core.service.authorized;

import java.time.LocalDate;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.model.ticket.Ticket;

public interface AuditService extends AuthorizedService {

    void deleteTicket(Ticket ticket);
    
    ActiveTicket createActiveTicketWithTimeSpecified(Book book, Client client, LocalDate startDate);

    ClosedTicket createClosedTicketWithTimeSpecified(Book book, Client client, LocalDate startDate, LocalDate endDate);
}
