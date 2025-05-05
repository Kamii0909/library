package hust.kien.project.core.book;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;

import hust.kien.project.core.author.AuthorId;

public interface BookMetadata {
    String name();

    @Nullable
    LocalDate releasedDate();

    List<@NonNull AuthorId> authors();
}
