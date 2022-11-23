package hust.kien.project.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;

public interface LibraryMetadataService {

    public Book saveOrUpdateBook(Book book);

    public Author saveOrUpdateAuthor(Author author);

    public Client saveOrUpdateClient(Client client);

    public void deleteBook(Book book);

    public void deleteAuthor(Author author);

    public void deleteClient(Client client);

    public <T> List<T> dynamicFind(Specification<T> spec);

    public List<Book> findBookByExactName(String name);

    public List<Author> findAuthorByNameContains(String name);

    public List<Book> dynamicFindBook(BookSpecificationBuilder specs);

}
