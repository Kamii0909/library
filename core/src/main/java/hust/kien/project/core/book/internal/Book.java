package hust.kien.project.core.book.internal;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JavaType;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import hust.kien.project.core.LocalDateAsStringType;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.BookEdges;
import hust.kien.project.core.book.BookId;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
class Book implements BookEdges {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BookId id;

    private String name;

    @JavaType(LocalDateAsStringType.class)
    private @Nullable LocalDate releasedDate;

    @ElementCollection
    @CollectionTable(name = "Book_Author", joinColumns = @JoinColumn(name = "books_id"))
    @Column(name = "authors_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<AuthorId> authors;

    @SuppressWarnings("null")
    protected Book() {
        // Hibernate
    }

    @SuppressWarnings("null")
    Book(String name, @Nullable LocalDate releasedDate, List<@NonNull AuthorId> authors) {
        this.name = name;
        this.releasedDate = releasedDate;
        this.authors = authors;
    }

    @Override
    public BookId id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public @Nullable LocalDate releasedDate() {
        return releasedDate;
    }

    @Override
    public List<@NonNull AuthorId> authors() {
        return Collections.unmodifiableList(authors);
    }

    void setName(String name) {
        this.name = name;
    }

    void setReleasedDate(@Nullable LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

    void setAuthors(List<AuthorId> authors) {
        this.authors = authors;
    }
}
