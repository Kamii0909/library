package hust.kien.project.core.dao.book;

import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import hust.kien.project.core.book.BookSchema;
import hust.kien.project.core.model.book.Book;

public interface SchemaAwareBookRepository {
    List<Book> findAll(Specification<Book> specification, BookSchema schema);
}
