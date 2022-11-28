package hust.kien.project.model.book;

import hust.kien.project.model.LibraryPersistable;
import jakarta.persistence.Embedded;
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
public class Book implements LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;

    @Embedded
    private BookInfo bookInfo;

    @Embedded
    private BookStock bookStock;

    @Builder
    public Book(String name, int releasedYear, int stock, double reimburseCost) {
        this.bookInfo = BookInfo.builder()
            .name(name)
            .releasedYear(releasedYear)
            .build();
        this.bookStock = BookStock.builder()
            .stock(stock)
            .reimburseCost(reimburseCost)
            .build();
    }

    /**
     * Helper method
     */
}
