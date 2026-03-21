package hust.kien.project.core.book;

import java.util.List;

import com.google.common.annotations.Beta;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.book.internal.BookFilter;
import org.jspecify.annotations.Nullable;

public interface BookService {

    ReadonlyBook create(BookCreationRequest request);

    ReadonlyBook update(BookUpdateRequest request);

    BookMetadata delete(BookId bookId);

    @Nullable
    ReadonlyBook findById(BookId bookId);

    List<ReadonlyBook> find(BookFilter filter);

    List<String> genresOf(BookId bookId);

    // These methods exploit the fact that activeClients subsystems for Book, Author
    // and Book <-> Author is in the same service.
    @Beta
    List<ReadonlyBook> booksOf(AuthorId authorId);


    record BookWithGenres(BookMetadata book, List<String> genres) {
    }

    @Nullable
    BookWithGenres findByIdWithGenres(BookId bookId);
}
