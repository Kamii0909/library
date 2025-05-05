package hust.kien.project.core.book.internal;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.BookCreationRequest;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.BookUpdateRequest;
import hust.kien.project.core.book.ReadonlyBook;

@Service
@Transactional
class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;

    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public Book create(BookCreationRequest request) {
        Book book = new Book(request.name(), request.releasedDate(), request.authors());
        return bookRepository.persist(book);
    }

    @Override
    public Book update(BookUpdateRequest request) {
        Book book = bookRepository.find(request.id());

        if (book == null)
            throw bookNotFound(request.id());

        String name = request.name();
        if (name != null)
            book.setName(name);

        NullableUpdate<LocalDate> releasedDate = request.releasedDate();
        if (releasedDate != null)
            book.setReleasedDate(releasedDate.value());

        List<AuthorId> authors = request.authors();
        if (authors != null)
            book.setAuthors(authors);

        return bookRepository.merge(book);
    }

    private IllegalArgumentException bookNotFound(BookId id) {
        return new IllegalArgumentException("Book " + id + " not found");
    }

    @Override
    public Book delete(BookId id) {
        Book book = bookRepository.find(id);

        if (book == null)
            throw bookNotFound(id);

        bookRepository.deleteById(id);
        return book;
    }

    @Override
    public List<ReadonlyBook> find(BookFilter filter) {
        List<@NonNull Book> books = bookRepository.find(filter.build());
        return covarianceCast(books);
    }

    private static List<ReadonlyBook> covarianceCast(List<@NonNull Book> books) {
        @SuppressWarnings({ "unchecked", "rawtypes" })
        List<ReadonlyBook> result = (List) books;
        return result;
    }
}
