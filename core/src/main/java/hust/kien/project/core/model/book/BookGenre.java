package hust.kien.project.core.model.book;

import java.util.Set;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

@Entity
public class BookGenre implements LibraryLocatable, LibraryPersistable {
    @Id
    private String name;

    @ManyToMany(mappedBy = "bookInfo.bookGenres")
    private Set<Book> booksWithThisGenre;

    public Set<Book> getBooksWithThisGenre() {
        return booksWithThisGenre;
    }

    public void addBook(Book book) {
        booksWithThisGenre.add(book);
        book.getBookInfo().getBookGenres().add(this);
    }

    public void setBooksWithThisGenre(Set<Book> booksWithThisGenre) {
        this.booksWithThisGenre = booksWithThisGenre;
    }

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookGenre(" + this.getName() + ")";
    }

    public BookGenre() {}

    public BookGenre(final String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        BookGenre other = (BookGenre) obj;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        return true;
    }
}
