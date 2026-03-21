package hust.kien.project.core.ticket;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.client.Client;
import hust.kien.project.core.client.ClientId;

import java.time.LocalDate;
import java.util.List;

public interface TicketService {
    Ticket createTicket(BookId bookId, ClientId clientId, LocalDate start);

    void closeTicket(TicketId ticketId);

    List<Ticket> activeTicketsOf(BookId bookId);

    //FIXME
    default List<Ticket> find(TicketFilter filter) {
        return List.of();
    }
}
