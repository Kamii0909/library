package hust.kien.project.model.author;

import hust.kien.project.model.LibraryLocatable;
import hust.kien.project.model.LibraryPersistable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(includeFieldNames = false)
@NoArgsConstructor
public class Author implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;

    private AuthorInfo authorInfo;

    public Author(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    @Builder
    public Author(String name, int age) {
        this.authorInfo = AuthorInfo.builder()
            .name(name)
            .age(age)
            .build();
    }

}
