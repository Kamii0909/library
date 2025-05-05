package hust.kien.project.core.author;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JavaType;
import org.jspecify.annotations.Nullable;

import hust.kien.project.core.LocalDateAsStringType;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Author implements LibraryLocatable, LibraryPersistable, ReadonlyAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @JavaType(LocalDateAsStringType.class)
    @Column(name = "date_of_birth")
    private @Nullable LocalDate dateOfBirth;

    @ElementCollection
    @CollectionTable(name = "Book_Author", joinColumns = @JoinColumn(name = "authors_id"))
    @Column(name = "books_id")
    @Fetch(FetchMode.SUBSELECT)
    private List<BookId> books;

    @Deprecated
    @SuppressWarnings("all")
    protected Author() {
        // For Hibernate
    }

    @SuppressWarnings("null")
    public Author(String name, int age) {
        this.name = name;
        this.dateOfBirth = LocalDate.now().minusYears(age);
        this.books = new ArrayList<>();
    }

    void replaceBooks(List<BookId> bookIds) {
        this.books.clear();
        this.books.addAll(bookIds);
    }

    void addBook(BookId bookId) {
        List<BookId> books = this.books;

        if (!books.contains(bookId))
            books.add(bookId);
    }

    void removeBook(BookId bookId) {
        List<BookId> books = this.books;

        if (!books.remove(bookId)) {
            throw new IllegalArgumentException("Book " + bookId + " not found in Author " + id);
        }
    }

    void setName(String name) {
        this.name = name;
    }

    void setDateOfBirth(LocalDate dob) {
        this.dateOfBirth = dob;
    }

    @Override
    public Long id() {
        return id;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public int age() {
        return (int) ChronoUnit.YEARS.between(Objects.requireNonNullElse(dateOfBirth, LocalDate.now()),
                LocalDate.now());
    }
}
