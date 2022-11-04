package hust.kien.project.dao.bookdao;

import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface BookLibraryDao extends LibraryDao<Book, Long> {
    //-------------------------
    // FIND BY BOOK INFO
    //-------------------------

    //------- Optional<Book> Name -------
    public List<Book> findByBookName(String name);
    public Optional<Book> findAnyByBookName(String name);

    //---- Released Year ------
    public List<Book> findByReleasedYear(int year);
    public Optional<Book> findAnyByReleasedYear(int year);

    public List<Book> findByReleasedYearBetween(int from, int to);
    public Optional<Book> findAnyByReleasedYearBetween(int from, int to);

    //------- Author(s) --------
    public List<Book> findByAuthor(Author author);
    public Optional<Book> findAnyByAuthor(Author author);
    /**
     * Find books which are written by at least one of {@code authors}
     *
     * @see #findWrittenByAll(Collection)
     */
    public List<Book> findWrittenByAtLeastOne(Collection<Author> authors);
    public Optional<Book> findAnyWrittenByAtLeastOne(Collection<Author> authors);

    /**
     * Find books which are written by one of {@code authors}
     *
     * @see #findAnyWrittenByAtLeastOne(Collection)
     */
    public List<Book> findWrittenByAll(Collection<Author> authors);
    public Optional<Book> findAnyWrittenByAll(Collection<Author> authors);

    //------ Genre(s) --------
    public List<Book> findFromGenre(BookGenre genre);
    public Optional<Book> findAnyFromGenre(BookGenre genre);

    public List<Book> findMatchAtLeastOneGenre(Collection<BookGenre> genres);
    public Optional<Book> findAnyMatchAtLeastOneGenre(Collection<BookGenre> genres);

    public List<Book> findMatchAllGenres(Collection<BookGenre> genres);
    public Optional<Book> findAnyMatchAllGenres(Collection<BookGenre> genres);

    //--- Composite criteria ---
    /**
     * A clunky method for composite criteria query
     * <p>
     * {@code BookInfo} maybe null in some field, {@code authors} and {@code genres} field is necessary managed by
     * persistence context
     * </p>
     */
    public List<Book> findFromBookInfo(BookInfo bookInfo);


}
