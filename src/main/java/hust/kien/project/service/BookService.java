package hust.kien.project.service;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.bookdao.BookRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;

public class BookService {
    private BookRepository bookRepository;

    public BookService(SessionFactory sessionFactory) {
        bookRepository = BookRepository.getInstance(sessionFactory);
    }

    private void voidTransactionWrapper(Runnable func) {
        bookRepository.beginTransaction();
        func.run();
        bookRepository.commitTransaction();
    }

    private List<Book> listTransactionWrapper(Supplier<List<Book>> func) {
        bookRepository.beginTransaction();
        List<Book> books = func.get();
        bookRepository.commitTransaction();

        return books;
    }

    private Optional<Book> optionalTransactionWrapper(Supplier<Optional<Book>> func) {
        bookRepository.beginTransaction();
        Optional<Book> book = func.get();
        bookRepository.commitTransaction();

        return book;
    }

    public List<Book> fetch(int amount) {
        return listTransactionWrapper(() ->  bookRepository.fetch(amount));
    }

    public void save(Book entity) {
        voidTransactionWrapper(() -> bookRepository.save(entity));
    }

    public void delete(Book entity) {
        voidTransactionWrapper(() -> bookRepository.delete(entity));

    }

    public void deleteAll() {
        voidTransactionWrapper(() -> bookRepository.deleteAll());

    }

    public void update(Book entity) {
        voidTransactionWrapper(() -> bookRepository.update(entity));
    }

    public List<Book> findByBookName(String name) {
        return listTransactionWrapper(() ->  bookRepository.findByBookName(name));
    }

    public Optional<Book> findAnyByBookName(String name) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByBookName(name));
    }

    public List<Book> findByReleasedYear(int year) {
        return listTransactionWrapper(() ->  bookRepository.findByReleasedYear(year));
    }

    public Optional<Book> findAnyByReleasedYear(int year) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByReleasedYear(year));
    }

    public List<Book> findByReleasedYearBetween(int from, int to) {
        return listTransactionWrapper(() ->  bookRepository.findByReleasedYearBetween(from, to));
    }

    public Optional<Book> findAnyByReleasedYearBetween(int from, int to) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByReleasedYearBetween(from, to));
    }

    public List<Book> findByAuthor(Author author) {
        return listTransactionWrapper(() ->  bookRepository.findByAuthor(author));
    }

    public Optional<Book> findAnyByAuthor(Author author) {
       return optionalTransactionWrapper(() ->  bookRepository.findAnyByAuthor(author));
    }

    public List<Book> findWrittenByAtLeastOne(Collection<Author> authors) {
        return listTransactionWrapper(() ->  bookRepository.findWrittenByAtLeastOne(authors));
    }

    public Optional<Book> findAnyWrittenByAtLeastOne(Collection<Author> authors) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyWrittenByAtLeastOne(authors));
    }

    public List<Book> findWrittenByAll(Collection<Author> authors) {
        return listTransactionWrapper(() ->  bookRepository.findWrittenByAll(authors));
    }

    public Optional<Book> findAnyWrittenByAll(Collection<Author> authors) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyWrittenByAll(authors));
    }

    public List<Book> findFromGenre(BookGenre genre) {
        return listTransactionWrapper(() ->  bookRepository.findFromGenre(genre));
    }

    public Optional<Book> findAnyFromGenre(BookGenre genre) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyFromGenre(genre));
    }

    public List<Book> findMatchAtLeastOneGenre(Collection<BookGenre> genres) {
        return listTransactionWrapper(() ->  bookRepository.findMatchAtLeastOneGenre(genres));
    }

    public Optional<Book> findAnyMatchAtLeastOneGenre(Collection<BookGenre> genres) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyMatchAtLeastOneGenre(genres));
    }

    public List<Book> findMatchAllGenres(Collection<BookGenre> genres) {
        return listTransactionWrapper(() ->  bookRepository.findMatchAllGenres(genres));
    }

    public Optional<Book> findAnyMatchAllGenres(Collection<BookGenre> genres) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyMatchAllGenres(genres));
    }

    public List<Book> findFromBookInfo(BookInfo bookInfo) {
        return listTransactionWrapper(() ->  bookRepository.findFromBookInfo(bookInfo));
    }

    public List<Book> findByStockBetween(int from, int to) {
        return listTransactionWrapper(() ->  bookRepository.findByStockBetween(from, to));
    }

    public Optional<Book> findAnyByStockBetween(int from, int to) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByStockBetween(from, to));
    }

    public List<Book> findByReimburseCostBetween(double from, double to) {
        return listTransactionWrapper(() ->  bookRepository.findByReimburseCostBetween(from, to));
    }

    public Optional<Book> findAnyByReimburseCostBetween(double from, double to) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByReimburseCostBetween(from, to));
    }

    public List<Book> findByAtLeastOneContractDateFrom(LocalDate from, LocalDate to) {
        return listTransactionWrapper(() ->  bookRepository.findByAtLeastOneContractDateFrom(from, to));
    }

    public Optional<Book> findAnyByAtLeastOneContractDateFrom(LocalDate from, LocalDate to) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByAtLeastOneContractDateFrom(from, to));
    }

    public List<Book> findByAllContractDateFrom(LocalDate from, LocalDate to) {
        return listTransactionWrapper(() ->  bookRepository.findByAllContractDateFrom(from, to));
    }

    public Optional<Book> findAnyByAllContractDateFrom(LocalDate from, LocalDate to) {
        return optionalTransactionWrapper(() ->  bookRepository.findAnyByAllContractDateFrom(from, to));
    }
}
