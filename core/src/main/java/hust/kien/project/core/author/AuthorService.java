package hust.kien.project.core.author;

import java.util.List;

import org.jspecify.annotations.NonNull;

public interface AuthorService {

    ReadonlyAuthor create(AuthorCreationRequest request);

    ReadonlyAuthor update(AuthorUpdateRequest request);

    ReadonlyAuthor delete(AuthorId id);

    List<@NonNull ReadonlyAuthor> find(AuthorFilter filter);

}
