package hust.kien.project.core.author;

import java.time.LocalDate;
import java.util.List;

import org.jspecify.annotations.Nullable;

import hust.kien.project.core.book.BookId;

public record AuthorCreationRequest(String name, @Nullable LocalDate dateOfBirth, List<BookId> books) {

}
