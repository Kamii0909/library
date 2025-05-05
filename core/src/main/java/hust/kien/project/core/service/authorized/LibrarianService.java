package hust.kien.project.core.service.authorized;

import java.util.List;
import org.springframework.data.domain.Pageable;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.service.dynamic.GeneralSpecificationBuilder;

public interface LibrarianService extends AuthorizedService {

    <T extends LibraryPersistable> T saveOrUpdate(T entity); 

    <T extends LibraryPersistable> void delete(T entity); 

    <T extends LibraryLocatable> List<T> dynamicFind(GeneralSpecificationBuilder<T> builder);

    <T extends LibraryLocatable> List<T> dynamicFind(GeneralSpecificationBuilder<T> builder, Pageable pageable);

    ActiveTicket createActiveTicket(Book book, Client client);

    ClosedTicket closeActiveTicket(ActiveTicket ticket);

}
