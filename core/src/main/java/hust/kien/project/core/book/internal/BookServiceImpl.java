package hust.kien.project.core.book.internal;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.*;
import hust.kien.project.core.bookauthor.BookAuthorService;
import org.hibernate.Hibernate;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static hust.kien.project.core.Utils.covarianceCast;

@Service
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final BookAuthorService bookAuthorService;

    public BookServiceImpl(
            BookRepository bookRepository,
            BookAuthorService bookAuthorService) {

        this.bookRepository = bookRepository;
        this.bookAuthorService = bookAuthorService;
    }

    @Override
    @Transactional
    public Book create(BookCreationRequest request) {
        Book book = new Book(request.name(), request.releasedDate(), request.genres());
        book = bookRepository.persist(book);
        bookAuthorService.setAuthorsForBook(book.id(), request.authors());
        return book;
    }

    @Override
    @Transactional
    public Book update(BookUpdateRequest request) {
        BookId bookId = request.id();
        Book book = bookRepository.find(bookId.id());

        if (book == null)
            throw bookNotFound(bookId);

        String name = request.name();
        if (name != null) {
            book.setName(name);
        }

        NullableUpdate<LocalDate> releasedDate = request.releasedDate();
        if (releasedDate != null) {
            book.setReleasedDate(releasedDate.value());
        }

        List<AuthorId> authors = request.authors();
        if (authors != null) {
            bookAuthorService.setAuthorsForBook(bookId, authors);
        }

        return bookRepository.merge(book);
    }

    private IllegalArgumentException bookNotFound(BookId id) {
        return new IllegalArgumentException("Book " + id + " not found");
    }

    @Override
    @Transactional
    public Book delete(BookId bookId) {
        Long id = bookId.id();
        Book book = bookRepository.find(id);

        if (book == null)
            throw bookNotFound(bookId);

        bookRepository.deleteById(id);
        return book;
    }

    @Override
    @Transactional
    public @Nullable ReadonlyBook findById(BookId bookId) {
        return bookRepository.find(bookId.id());
    }

    @Override
    @Transactional(readOnly = true)
    public List<String> genresOf(BookId bookId) {
        return bookRepository.genresOf(bookId.id());
    }

    @Override
    @Transactional
    public List<ReadonlyBook> find(BookFilter filter) {
        List<Book> books = bookRepository.find(filter.build());
        return covarianceCast(books);
    }

    @Override
    @Transactional
    public List<ReadonlyBook> booksOf(AuthorId authorId) {
        return covarianceCast(bookRepository.booksOf(authorId));
    }

    @Override
    @Transactional
    public @Nullable BookWithGenres findByIdWithGenres(BookId bookId) {
        Book book = bookRepository.find(bookId.id());
        if (book == null) return null;
        List<String> genres = book.genres();
        Hibernate.initialize(genres);
        return new BookWithGenres(book, genres);
    }
}
