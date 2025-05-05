package hust.kien.project.core.book;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.Nullable;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.author.AuthorId;

public final class BookUpdateRequest {
    private final BookId id;
    private @Nullable String name;
    private @Nullable NullableUpdate<LocalDate> releasedDate;
    private @Nullable List<AuthorId> authors;

    public BookUpdateRequest(BookId id) {
        this.id = id;
    }

    public BookId id() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setReleasedDate(LocalDate releasedDate) {
        this.releasedDate = new NullableUpdate<LocalDate>(releasedDate);
    }

    public void setAuthors(List<AuthorId> authors) {
        this.authors = authors;
    }

    public @Nullable String name() {
        return name;
    }

    public @Nullable NullableUpdate<LocalDate> releasedDate() {
        return releasedDate;
    }

    public @Nullable List<AuthorId> authors() {
        return authors;
    }
}
