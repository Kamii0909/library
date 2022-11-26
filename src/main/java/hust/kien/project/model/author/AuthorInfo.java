package hust.kien.project.model.author;

import java.util.HashSet;
import java.util.Set;
import hust.kien.project.model.book.Book;
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
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@NoArgsConstructor
@SuperBuilder
public class AuthorInfo {
    
    @ToString.Include
    private String name;

    @ToString.Include
    private int age;

    @ManyToMany(mappedBy = "bookInfo.authors")
    @Builder.Default
    private Set<Book> books = new HashSet<>();

}
