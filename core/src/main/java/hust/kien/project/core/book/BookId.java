package hust.kien.project.core.book;

import jakarta.persistence.Embeddable;

@Embeddable
public record BookId(Long id) {
    public static BookId of(Long id) {
        return new BookId(id);
    }
}
