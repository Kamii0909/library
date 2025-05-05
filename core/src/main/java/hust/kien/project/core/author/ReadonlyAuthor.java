package hust.kien.project.core.author;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import org.jspecify.annotations.Nullable;

public interface ReadonlyAuthor {
    AuthorId id();

    String name();

    default int age() {
        LocalDate dob = dateOfBirth();
        if (dob == null)
            return -1;

        return (int) ChronoUnit.YEARS.between(dob, LocalDate.now());
    }

    @Nullable
    LocalDate dateOfBirth();
}
