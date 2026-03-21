package hust.kien.project.core.author;

import com.google.common.annotations.Beta;
import hust.kien.project.core.book.BookId;

import java.util.List;

public interface AuthorService {

    ReadonlyAuthor create(AuthorCreationRequest request);

    ReadonlyAuthor update(AuthorUpdateRequest request);

    ReadonlyAuthor delete(AuthorId id);

    List<ReadonlyAuthor> find(AuthorFilter filter);

    @Beta
    List<ReadonlyAuthor> authorsOf(BookId bookId);
}
