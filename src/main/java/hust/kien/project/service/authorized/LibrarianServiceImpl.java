package hust.kien.project.service.authorized;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.LibraryLocatable;
import hust.kien.project.model.LibraryPersistable;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;
import hust.kien.project.service.internal.LibraryMetadataService;
import hust.kien.project.service.internal.TicketService;

@Service
public class LibrarianServiceImpl implements LibrarianService {

    @Autowired
    private LibraryMetadataService metadataService;

    @Autowired
    private TicketService ticketService;

    @Override
    @SuppressWarnings("unchecked")
    public Class<LibrarianService> getRuntimeServiceClass() {
        return LibrarianService.class;
    }

    @Override
    public <T extends LibraryPersistable> T saveOrUpdate(T entity) {
        return metadataService.saveOrUpdate(entity);
    }

    @Override
    public <T extends LibraryLocatable> List<T> dynamicFind(
        GeneralLibrarySpecificationBuilder<T> builder) {
        return metadataService.dynamicFind(builder);
    }

    @Override
    public ActiveTicket createActiveTicket(Book book, Client client) {
        return ticketService.createActiveTicket(book, client);
    }

    @Override
    public ClosedTicket closeActiveTicket(ActiveTicket ticket) {
        return ticketService.closeActiveTicket(ticket);
    }

}
