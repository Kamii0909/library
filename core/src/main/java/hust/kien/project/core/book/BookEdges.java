package hust.kien.project.core.book;

import java.util.List;

import org.jspecify.annotations.NonNull;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.author.ReadonlyAuthor;

public interface BookEdges extends ReadonlyBook {
    default List<@NonNull ReadonlyAuthor> edgeAuthors() {
        throw new UnsupportedOperationException("This edge is not fetched");
    }

    @Override
    default List<@NonNull AuthorId> authors() {
        return edgeAuthors().stream().map(ReadonlyAuthor::id).toList();
    }
}
