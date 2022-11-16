package hust.kien.project.model.rent;

import java.time.LocalDate;

import jakarta.persistence.*;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Formula;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;

@Entity
@Cacheable
@org.hibernate.annotations.Cache(region = "Book Rent Contract", usage = CacheConcurrencyStrategy.READ_WRITE)
public class BookRentContract {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE)
    private Client client;

    @Formula("endDate > date('now', 'localtime')")
    private boolean isActive;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate startDate;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;

    public BookRentContract() {}

    public BookRentContract(Book book, Client client, LocalDate startDate) {
        this.book = book;
        this.client = client;
        this.isActive = true;
        this.startDate = startDate;
        this.endDate = LocalDate.of(2999, 12, 31);
    }

    public BookRentContract(Book book, Client client, LocalDate startDate, LocalDate endDate) {
        this.book = book;
        this.client = client;
        this.isActive = false;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

}
