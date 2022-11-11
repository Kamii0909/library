package hust.kien.project.model.rent;

import java.time.LocalDate;
import org.hibernate.annotations.Formula;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import jakarta.persistence.CascadeType;
import jakarta.persistence.ConstraintMode;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ForeignKey;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class BookRentContract {
    @Id
    @GeneratedValue
    private Long id;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "book_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
    private Book book;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "client_id", foreignKey = @ForeignKey(value = ConstraintMode.CONSTRAINT))
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
