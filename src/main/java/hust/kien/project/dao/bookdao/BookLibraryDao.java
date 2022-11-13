package hust.kien.project.dao.bookdao;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;

public interface BookLibraryDao extends LibraryDao<Book, Long> {
    //-------------------------
    // FIND BY BOOK INFO
    //-------------------------

    //------- Book Name -------
    public List<Book> findByBookName(String name);

    //---- Released Year ------
    public List<Book> findByReleasedYear(int year);

    public List<Book> findByReleasedYearBetween(int from, int to);

    //------- Author(s) --------
    public List<Book> findByAuthor(Author author);
    /**
     * Find books which are written by at least one of {@code authors}
     *
     * @see #findWrittenByAll(Collection)
     */
    public List<Book> findWrittenByAtLeastOne(Collection<Author> authors);

    /**
     * Find books which are written by one of {@code authors}
     *

     */
    public List<Book> findWrittenByAll(Collection<Author> authors);

    //------ Genre(s) --------
    public List<Book> findFromGenre(BookGenre genre);

    public List<Book> findMatchAtLeastOneGenre(Collection<BookGenre> genres);

    public List<Book> findMatchAllGenres(Collection<BookGenre> genres);

    //--- Composite criteria ---
    /**
     * A clunky method for composite criteria query
     * <p>
     * {@code bookInfo} maybe null in some field, {@code authors} and {@code genres} field is necessary managed by
     * persistence context
     * </p>
     */
    public List<Book> findFromBookInfo(BookInfo bookInfo);


    //-------------------------
    // FIND BY BOOK STOCK
    //-------------------------
    
    //- Current library stock -
    public List<Book> findByStockBetween(int from, int to);

    //---- Reimburse Cost -----
    public List<Book> findByReimburseCostBetween(double from, double to);

    //----- Contract date -----
    /**
     * Find all Book where at least one contract satisfy date requirements <p>
     * <li> For completed contract,  {@code startDate > from} and {@code endDate < to}
     * <li> For ongoing contract, {@code startDate > from} and {@code endDate} is not restricted
     * 
     * @see #findByAllContractDateFrom(LocalDate, LocalDate)
     */
    public List<Book> findByAtLeastOneContractDateFrom(LocalDate from, LocalDate to);

    /**
     * Find all Book where all contracts satisfy date requirements <p>
     * <li> For completed contract,  {@code startDate > from} and {@code endDate < to}
     * <li> For ongoing contract, {@code startDate > from} and {@code endDate} is not restricted
     * 
     * @see #findByAtLeastOneContractDateFrom(LocalDate, LocalDate)
     */
    public List<Book> findByAllContractDateFrom(LocalDate from, LocalDate to);


}
