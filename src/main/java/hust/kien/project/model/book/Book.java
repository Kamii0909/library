package hust.kien.project.model.book;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Book {
    @Id
    @GeneratedValue
    private int id;
    @Embedded private BookInfo bookInfo;
    @Embedded private BookStock bookStock;

    public Book() {
    }

    public Book(BookInfo bookInfo) {
        this.bookInfo = bookInfo;
    }

    public int getid() {
        return id;
    }

    public void setid(int id) {
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
    
}
