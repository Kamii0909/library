package hust.kien.project.core.ticket;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.ReadonlyBook;
import hust.kien.project.core.client.ClientId;
import hust.kien.project.core.client.ClientService;
import hust.kien.project.core.client.ReadonlyClient;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
class TicketServiceImpl implements TicketService {
    private final TicketRepository repository;
    private final BookService bookService;
    private final ClientService clientService;

    TicketServiceImpl(TicketRepository repository, BookService bookService, ClientService clientService) {
        this.repository = repository;
        this.bookService = bookService;
        this.clientService = clientService;
    }

    @Transactional
    @Override
    public Ticket createTicket(BookId bookId, ClientId clientId, LocalDate start) {
        ReadonlyBook book = bookService.findById(bookId);
        if (book == null) {
            throw new IllegalArgumentException("Book " + bookId + " does not exists");
        }
        ReadonlyClient client = clientService.findById(clientId);
        if (client == null) {
            throw new IllegalArgumentException("Client " + clientId + " does not exists");
        }

        Ticket ticket = new Ticket(book, client, start, null);
        repository.persist(ticket);
        return ticket;
    }

    @Transactional
    @Override
    public void closeTicket(TicketId ticketId) {
        Ticket ticket = repository.find(ticketId.id());
        if (ticket == null) throw new IllegalArgumentException("Ticket " + ticketId + " is not found");

        if (ticket.isClosed()) {
            throw new IllegalArgumentException("Ticket is already closed");
        }

        ticket.closeNow();
    }


    @Transactional
    @Override
    public List<Ticket> activeTicketsOf(BookId bookId) {
        return repository.activeTicketsOf(bookId.id());
    }
}
