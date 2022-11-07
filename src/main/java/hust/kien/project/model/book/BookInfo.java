package hust.kien.project.model.book;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;
import hust.kien.project.model.author.Author;

@Embeddable
public class BookInfo {
    private String bookName;
    private int releasedYear;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Book_BookGenre")
    private Set<BookGenre> bookGenres = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "Book_Author")
    private Set<Author> authors = new HashSet<>();


    public BookInfo() {}

    public BookInfo(String bookName, int releasedYear) {
        this.bookName = bookName;
        this.releasedYear = releasedYear;
    }

    public Set<BookGenre> getBookGenres() {
        return bookGenres;
    }

    public void setBookGenres(Set<BookGenre> bookGenres) {
        this.bookGenres = bookGenres;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
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
