package hust.kien.project.service.auth;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;

public interface AuditService extends AuthorizedService {
    
    ActiveTicket createActiveTicketFrom(Book book, Client client, LocalDate startDate);

    ClosedTicket createClosedTicketWithTime(Book book, Client client, LocalDate startDate, LocalDate endDate);
}
