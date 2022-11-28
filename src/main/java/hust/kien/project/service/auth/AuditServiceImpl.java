package hust.kien.project.service.auth;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.model.ticket.Ticket;
import hust.kien.project.service.internal.TicketService;

@Service
public class AuditServiceImpl implements AuditService {

    @Autowired
    private TicketService ticketService;

    @Override
    @SuppressWarnings("unchecked")
    public Class<AuditService> getRuntimeServiceClass() {
        return AuditService.class;
    }

    @Override
    public void deleteTicket(Ticket ticket) {
        ticketService.deleteTicket(ticket);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ActiveTicket createActiveTicketWithTimeSpecified(Book book, Client client,
        LocalDate startDate) {
        return ticketService.createActiveTicket(book, client, startDate);
    }

    @Override
    @SuppressWarnings("deprecation")
    public ClosedTicket createClosedTicketWithTimeSpecified(Book book, Client client,
        LocalDate startDate, LocalDate endDate) {
        return ticketService.createClosedTicket(book, client, startDate, endDate);
    }

}
