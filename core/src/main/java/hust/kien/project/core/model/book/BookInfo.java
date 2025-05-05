package hust.kien.project.core.model.book;

import java.util.List;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import hust.kien.project.core.author.Author;
import jakarta.persistence.Embeddable;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;

@Embeddable
public class BookInfo {
    private String name;
    
    private int releasedYear;
    
    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    private List<BookGenre> bookGenres;
    
    @ManyToMany
    @JoinTable(joinColumns = @JoinColumn(name = "books_id"))
    @Fetch(FetchMode.SUBSELECT)
    private List<Author> authors;
    
    @Deprecated
    protected BookInfo() {
        // For Hibernate
    }
    
    public BookInfo(String bookName, int releasedYear) {
        this.name = bookName;
        this.releasedYear = releasedYear;
    }
    
    List<BookGenre> getBookGenres() {
        return this.bookGenres;
    }
    
    @Deprecated
    public List<BookGenre> bookGenres() {
        return this.bookGenres;
    }
    
    public void removeGenre(BookGenre bookGenre) {
        this.bookGenres.remove(bookGenre);
    }
    
    public String name() {
        return this.name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public int releasedYear() {
        return this.releasedYear;
    }
    
    public void setReleasedYear(int releasedYear) {
        this.releasedYear = releasedYear;
    }
    
    @Deprecated
    public List<Author> authors() {
        return this.authors;
    }
    
    @Deprecated
    public void setBookGenres(List<BookGenre> bookGenres) {
        this.bookGenres = bookGenres;
    }
    
    @Deprecated
    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }
    
}
