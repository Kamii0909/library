package hust.kien.project.model.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(includeFieldNames = false)
@NoArgsConstructor
@AllArgsConstructor
public class BookGenre {
    @Id
    private String name;
}
