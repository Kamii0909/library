package hust.kien.project.core.book;

import java.util.List;

import hust.kien.project.core.book.internal.BookFilter;

public interface BookService {

    ReadonlyBook create(BookCreationRequest request);

    ReadonlyBook update(BookUpdateRequest request);

    BookMetadata delete(BookId bookId);

    List<ReadonlyBook> find(BookFilter filter);
}
