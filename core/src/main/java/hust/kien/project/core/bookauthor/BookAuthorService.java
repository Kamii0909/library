package hust.kien.project.core.bookauthor;

import com.google.common.annotations.Beta;
import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.ReadonlyBook;

import java.util.List;

public interface BookAuthorService {
    List<BookId> bookIdsOf(AuthorId authorId);

    List<AuthorId> authorIdsOf(BookId bookId);

    // Mutators
    void setAuthorsForBook(BookId bookId, List<AuthorId> authorIds);

}
