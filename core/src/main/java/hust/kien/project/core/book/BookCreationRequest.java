package hust.kien.project.core.book;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.Nullable;

import hust.kien.project.core.author.AuthorId;

public record BookCreationRequest(
        String name,
        @Nullable LocalDate releasedDate,
        List<AuthorId> authors,
        List<String> genres) {

}
