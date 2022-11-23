package hust.kien.project.model.book;

import java.util.HashSet;
import java.util.Set;
import org.hibernate.annotations.BatchSize;
import hust.kien.project.model.author.Author;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

@Embeddable
public class BookInfo {
    private String name;
    private int releasedYear;

    @ManyToMany(cascade = CascadeType.PERSIST)
    private Set<BookGenre> bookGenres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @BatchSize(size = 5)
    private Set<Author> authors = new HashSet<>();


    public BookInfo() {}

    public BookInfo(String bookName, int releasedYear) {
        this.name = bookName;
        this.releasedYear = releasedYear;
    }

    public Set<BookGenre> getBookGenres() {
        return bookGenres;
    }

    public void setBookGenres(Set<BookGenre> bookGenres) {
        this.bookGenres = bookGenres;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(Set<Author> authors) {
        this.authors = authors;
    }

    public int getReleasedYear() {
        return releasedYear;
    }

    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }


}
