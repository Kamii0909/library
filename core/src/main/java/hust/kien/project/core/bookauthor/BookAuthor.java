package hust.kien.project.core.bookauthor;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.BookId;
import jakarta.persistence.*;

@Entity
@Table(name = "Book_Author")
public class BookAuthor {

    @EmbeddedId
    private BookAuthorLink id;

    protected BookAuthor() {
        // Hibernate
    }

    BookAuthor(BookAuthorLink id) {
        this.id = id;
    }

    public BookId bookId() {
        return id.bookId();
    }

    public AuthorId authorId() {
        return id.authorId();
    }
}
