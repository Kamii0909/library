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

    public AuthorUpdateRequest(AuthorId id) {
        this.id = id.id();
    }

    public void setDob(@Nullable LocalDate dob) {
        this.dateOfBirth = new NullableUpdate<>(dob);
    }

    public void setName(String name) {
        this.name = name;
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

}
