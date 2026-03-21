package hust.kien.project.core.ticket;

import hust.kien.project.core.LocalDateAsStringType;
import hust.kien.project.core.book.ReadonlyBook;
import hust.kien.project.core.client.ClientId;
import hust.kien.project.core.client.ReadonlyClient;
import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;

import static hust.kien.project.core.Utils.idRequired;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * The first day of renting the book, inclusive
     */
    @JavaType(LocalDateAsStringType.class)
    private LocalDate start;

    /**
     * The last day of renting the book, inclusive
     */
    @JavaType(LocalDateAsStringType.class)
    private @Nullable LocalDate end;

    /**
     * Since we don't reuse id, even though this is not null, said book might already be deleted.
     */
    @Column(name = "bookId", nullable = false)
    private Long bookId;

    /**
     * Since we don't reuse id, even though this is not null, said client might already be deleted.
     */
    @Column(name = "clientId", nullable = false)
    private Long clientId;

    protected Ticket() {
        // Hibernate
    }

    public Ticket(ReadonlyBook book, ReadonlyClient client, LocalDate start, @Nullable LocalDate end) {
        this.bookId = book.id().id();
        this.clientId = client.id().id();

        this.start = start;
        if (end != null) {
            if (end.isBefore(start)) throw new IllegalArgumentException("start <= end");
            this.end = end;
        }
    }

    public boolean isClosed() {
        return this.end != null;
    }

    void closeNow() {
        closeAt(LocalDate.now());
    }

    void closeAt(LocalDate date) {
        if (date.isBefore(start)) {
            throw new IllegalStateException(
                    "Cannot close at " + date + "because " + date + " < start (" + start + ")");
        }
        this.end = date;
    }


    public TicketId id() {
        return TicketId.of(idRequired(id));
    }

    public LocalDate start() {
        return start;
    }

    public LocalDate end() {
        return end;
    }

    public Long bookId() {
        return bookId;
    }

    public ClientId clientId() {
        return ClientId.of(clientId);
    }
}
