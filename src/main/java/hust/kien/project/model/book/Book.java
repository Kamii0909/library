package hust.kien.project.model.book;

import jakarta.persistence.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Book {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private BookInfo bookInfo;
    @Embedded
    private BookStock bookStock;

    public Book() {}

    public Book(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
        this.bookStock = new BookStock();
    }

    public Book(BookInfo bookInfo, BookStock bookStock) {
        this.bookInfo = bookInfo;
        this.bookStock = bookStock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public BookInfo getBookInfo() {
        return bookInfo;
    }


    public void setBookInfo(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public BookStock getBookStock() {
        return bookStock;
    }

    public void setBookStock(BookStock bookStock) {
        this.bookStock = bookStock;
    }

    /**
     * Helper method
     */
}
