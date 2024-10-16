package hust.kien.project.core.model.book;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;
    @Embedded
    private BookInfo bookInfo;
    @Embedded
    private BookStock bookStock;

    public Book(String name, int releasedYear, int stock, double reimburseCost) {
        this.bookInfo = BookInfo.builder().name(name).releasedYear(releasedYear).build();
        this.bookStock = BookStock.builder().stock(stock).reimburseCost(reimburseCost).build();
    }

    public static class BookBuilder {
        private String name;
        private int releasedYear;
        private int stock;
        private double reimburseCost;

        BookBuilder() {}

        public BookBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public BookBuilder releasedYear(final int releasedYear) {
            this.releasedYear = releasedYear;
            return this;
        }

        public BookBuilder stock(final int stock) {
            this.stock = stock;
            return this;
        }

        public BookBuilder reimburseCost(final double reimburseCost) {
            this.reimburseCost = reimburseCost;
            return this;
        }

        public Book build() {
            return new Book(this.name, this.releasedYear, this.stock, this.reimburseCost);
        }

        @Override
        public String toString() {
            return "Book.BookBuilder(name=" + this.name + ", releasedYear=" + this.releasedYear
                + ", stock=" + this.stock + ", reimburseCost=" + this.reimburseCost + ")";
        }
    }

    public static BookBuilder builder() {
        return new BookBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public BookInfo getBookInfo() {
        return this.bookInfo;
    }

    public BookStock getBookStock() {
        return this.bookStock;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setBookInfo(final BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public void setBookStock(final BookStock bookStock) {
        this.bookStock = bookStock;
    }

    @Override
    public String toString() {
        return "Book(" + this.getId() + ", " + this.getBookInfo() + ", " + this.getBookStock()
            + ")";
    }

    public Book() {}
}
