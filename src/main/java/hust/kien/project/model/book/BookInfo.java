package hust.kien.project.model.book;

import java.util.HashSet;
import java.util.Set;
// import org.hibernate.annotations.BatchSize;
import hust.kien.project.model.author.Author;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true)
@NoArgsConstructor
@SuperBuilder
public class BookInfo {

    @ToString.Include
    private String name;

    @ToString.Include
    private int releasedYear;

    @ManyToMany
    // @BatchSize(size = 5)
    @Builder.Default
    private Set<BookGenre> bookGenres = new HashSet<>();

    @ManyToMany
    // @BatchSize(size = 5)
    @Builder.Default
    private Set<Author> authors = new HashSet<>();

    public BookInfo(String bookName, int releasedYear) {
        this.name = bookName;
        this.releasedYear = releasedYear;
    }
}
