package hust.kien.project.core.bookauthor;

import java.util.List;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.ReadonlyBook;

/**
 * A container for some fetched {@link Book} and {@link Author}, including their
 * edges. As a general rule, trying to access
 */
public interface BooksAuthors {
    /**
     * Returns the authors of a book, if it is fetched.
     * 
     * @throws IllegalArgumentException if the book is not fetched when creating
     *                                  this {@link BooksAuthorsImpl}.
     * @throws IllegalArugmentException if the authors edge of this book is not
     *                                  fetched when creating this
     *                                  {@link BooksAuthorsImpl}.
     */
    default List<ReadonlyAuthor> authorsOf(ReadonlyBook book) {
        return authorsOf(book.id());
    }

    List<ReadonlyAuthor> authorsOf(BookId id);

    default List<ReadonlyBook> booksOf(ReadonlyAuthor author) {
        return booksOf(author.id());
    }

    List<ReadonlyBook> booksOf(AuthorId id);

    List<ReadonlyBook> books();

    List<ReadonlyAuthor> authors();
}
