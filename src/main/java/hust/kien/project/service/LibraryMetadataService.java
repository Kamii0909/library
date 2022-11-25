package hust.kien.project.service;

import java.util.List;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;

public interface LibraryMetadataService {

    public <T> T saveOrUpdate(T entity);

    public <T> void delete(T entity);

    public <T> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> spec);

    public List<Book> findBookByExactName(String name);

    public List<Author> findAuthorByNameContains(String name);

}
