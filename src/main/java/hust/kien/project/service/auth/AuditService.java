package hust.kien.project.service.auth;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.Ticket;

public interface AuditService extends AuthorizedService {

    void deleteTicket(Ticket ticket);
    
    ActiveTicket createActiveTicketWithTimeSpecified(Book book, Client client, LocalDate startDate);

    ClosedTicket createClosedTicketWithTimeSpecified(Book book, Client client, LocalDate startDate, LocalDate endDate);
}
