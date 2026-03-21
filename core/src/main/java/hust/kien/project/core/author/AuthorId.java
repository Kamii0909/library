package hust.kien.project.core.author;

import jakarta.persistence.Embeddable;

@Embeddable
public record AuthorId(Long id) {

    public static AuthorId of(Long id) {
        return new AuthorId(id);
    }

}
