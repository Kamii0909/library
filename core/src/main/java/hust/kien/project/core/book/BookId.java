package hust.kien.project.core.book;

import jakarta.persistence.Embeddable;

@Embeddable
public record BookId(Long id) {
}
