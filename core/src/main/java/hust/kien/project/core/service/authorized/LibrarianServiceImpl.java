package hust.kien.project.core.service.authorized;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.service.dynamic.GeneralSpecificationBuilder;
import hust.kien.project.core.service.dynamic.ticket.ActiveTicketSpecificationBuilder;
import hust.kien.project.core.service.internal.LibraryMetadataService;
import hust.kien.project.core.service.internal.TicketService;

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
    public <T extends LibraryPersistable> void delete(T entity) {
        if (entity instanceof Client) {
            dynamicFind(new ActiveTicketSpecificationBuilder().client(((Client) entity)))
                .forEach(this::closeActiveTicket);
        }
        metadataService.delete(entity);
    }

    @Override
    public <T extends LibraryLocatable> List<T> dynamicFind(
        GeneralSpecificationBuilder<T> builder) {
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

    @Override
    public <T extends LibraryLocatable> List<T> dynamicFind(
        GeneralSpecificationBuilder<T> builder, Pageable pageable) {
        return metadataService.dynamicFind(builder, pageable);
    }

}
