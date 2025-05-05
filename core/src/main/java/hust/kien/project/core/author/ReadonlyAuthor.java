package hust.kien.project.core.author;

import java.util.List;

import hust.kien.project.core.model.book.Book;

public interface ReadonlyAuthor {
    Long id();

    String name();

    int age();

    /**
     * If this {@link ReadonlyAuthor} is created without specifying this edge,
     * accessing this property will throw.
     */
    default List<Book> books() {
        throw new UnsupportedOperationException();
    }
}
