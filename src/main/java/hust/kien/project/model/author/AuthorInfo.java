package hust.kien.project.model.author;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import hust.kien.project.model.book.Book;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

@Embeddable
public class AuthorInfo {
    
    private String name;

    private int age;

    @ManyToMany(mappedBy = "bookInfo.authors")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private Set<Book> books = new HashSet<>();

    public AuthorInfo() {
    }

    public AuthorInfo(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Set<Book> getBooks() {
        return books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
