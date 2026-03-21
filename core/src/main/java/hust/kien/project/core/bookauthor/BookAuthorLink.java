package hust.kien.project.core.bookauthor;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.BookId;
import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;

@Embeddable
class BookAuthorLink {
    @Column(name = "book_id")
    private Long bookId;

    @Column(name = "author_id")
    private Long authorId;

    protected BookAuthorLink() {
        // Hibernate
    }

    public BookAuthorLink(BookId bookId, AuthorId authorId) {
        this.bookId = bookId.id();
        this.authorId = authorId.id();
    }

    public BookId bookId() {
        return BookId.of(bookId);
    }

    public AuthorId authorId() {
        return AuthorId.of(authorId);
    }
}
