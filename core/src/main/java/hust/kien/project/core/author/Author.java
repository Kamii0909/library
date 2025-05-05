package hust.kien.project.core.author;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jspecify.annotations.Nullable;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import hust.kien.project.core.model.book.Book;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

@Entity
public class Author implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;
    
    private String name;
    
    private int age;
    
    @ElementCollection
    @CollectionTable(name = "Book_Author", joinColumns = @JoinColumn(name = "authors_id"))
    @Column(name = "books_id")
    private List<BookId> books;
    
    @Deprecated
    @SuppressWarnings("all")
    protected Author() {
        // For Hibernate
    }
    
    public Author(String name, int age) {
        this.name = name;
        this.age = age;
        this.books = new ArrayList<>();
    }
    
    public void addBook(Book book) {
        this.books.add(new BookId(book.getId()));
    }
    
    public Long id() {
        return Objects.requireNonNull(id);
    }
    
    public String name() {
        return name;
    }
    
    public int age() {
        return age;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public void setAge(int age) {
        this.age = age;
    }
}
