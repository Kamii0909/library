package hust.kien.project.service;

import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;

public interface LibraryMetadataService {

    public <T> T saveOrUpdate(T entity);

    public <T> void delete(T entity);

    public <T> List<T> dynamicFind(Specification<T> spec);

    public List<Book> findBookByExactName(String name);

    public List<Author> findAuthorByNameContains(String name);

}
