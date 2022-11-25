package hust.kien.project.service;

import java.util.List;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;

public interface LibraryMetadataService {

    <T> T saveOrUpdate(T entity);

    <T> void delete(T entity);

    <T> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> spec);

    List<Book> findBookByNameContains(String name);

    List<Author> findAuthorByNameContains(String name);

}
