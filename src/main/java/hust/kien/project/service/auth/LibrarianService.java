package hust.kien.project.service.auth;

import java.util.List;
import hust.kien.project.model.LibraryLocatable;
import hust.kien.project.model.LibraryPersistable;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;

public interface LibrarianService extends AuthorizedService {

    <T extends LibraryPersistable> T saveOrUpdate(T entity); 

    <T extends LibraryLocatable> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> builder);

    ActiveTicket createActiveTicket(Book book, Client client);

    ClosedTicket closeActiveTicket(ActiveTicket ticket);

}
