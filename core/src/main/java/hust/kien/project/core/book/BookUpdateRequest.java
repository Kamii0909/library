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
    private @Nullable List<String> genres;

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
        this.releasedDate = new NullableUpdate<>(releasedDate);
    }

    public void setAuthors(List<AuthorId> authors) {
        this.authors = authors;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
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

    public @Nullable List<String> genres() {
        return genres;
    }
}
