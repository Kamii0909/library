package hust.kien.project.core.model.ticket;

import java.time.LocalDate;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.client.Client;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Ticket implements LibraryLocatable {
    @Id
    @GeneratedValue
    private Long id;
    
    @ManyToOne
    private Book book;
    
    @ManyToOne
    private Client client;
    
    @Convert(converter = LocalDateConverter.class)
    private LocalDate startDate;
    
    public abstract static class TicketBuilder<C extends Ticket, B extends TicketBuilder<C, B>> {
        private Long id;
        
        private Book book;
        
        private Client client;
        
        private LocalDate startDate;
        
        protected abstract B self();
        
        public abstract C build();
        
        public B id(final Long id) {
            this.id = id;
            return self();
        }
        
        public B book(final Book book) {
            this.book = book;
            return self();
        }
        
        public B client(final Client client) {
            this.client = client;
            return self();
        }
        
        public B startDate(final LocalDate startDate) {
            this.startDate = startDate;
            return self();
        }
    }
    
    protected Ticket(final TicketBuilder<?, ?> b) {
        this.id = b.id;
        this.book = b.book;
        this.client = b.client;
        this.startDate = b.startDate;
    }
    
    protected Ticket() {
    }
    
    public Long getId() {
        return this.id;
    }
    
    public Book getBook() {
        return this.book;
    }
    
    public Client getClient() {
        return this.client;
    }
    
    public LocalDate getStartDate() {
        return this.startDate;
    }
    
    public void setId(final Long id) {
        this.id = id;
    }
    
    public void setBook(final Book book) {
        this.book = book;
    }
    
    public void setClient(final Client client) {
        this.client = client;
    }
    
    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }
    
    @Override
    public String toString() {
        return "Ticket(id=" + this.getId() + ", book=" + this.getBook() + ", client="
                + this.getClient() + ", startDate=" + this.getStartDate() + ")";
    }
}
