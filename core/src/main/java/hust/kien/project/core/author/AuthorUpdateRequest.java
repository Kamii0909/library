package hust.kien.project.core.author;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.Nullable;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.book.BookId;

public final class AuthorUpdateRequest {
    private final Long id;
    private @Nullable String name;
    private @Nullable NullableUpdate<LocalDate> dateOfBirth;
    private @Nullable BookChanges books;

    public sealed interface BookChanges {
        public record Diff(List<BookId> adds, List<BookId> removes) implements BookChanges {
            public Diff {
                for (BookId adding : adds) {
                    if (removes.contains(adding))
                        throw new IllegalArgumentException("Cannot add and remove " + adding + " at the same time");
                }
            }
        }

        public record Replace(List<BookId> all) implements BookChanges {
        }
    }

    public AuthorUpdateRequest(Long id) {
        this.id = id;
    }

    public void setDob(@Nullable LocalDate dob) {
        this.dateOfBirth = new NullableUpdate<LocalDate>(dob);
    }

    public void setName(String name) {
        this.name = name;
    }

    public void books(BookChanges changes) {
        this.books = changes;
    }

    public Long id() {
        return id;
    }

    @Nullable
    public String name() {
        return name;
    }

    @Nullable
    public NullableUpdate<LocalDate> dob() {
        return dateOfBirth;
    }

    @Nullable
    public BookChanges bookChanges() {
        return books;
    }

}
