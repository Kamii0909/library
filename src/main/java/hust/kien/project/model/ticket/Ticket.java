package hust.kien.project.model.ticket;

import java.time.LocalDate;
import hust.kien.project.model.LibraryLocatable;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@NoArgsConstructor
@SuperBuilder
@Getter
@Setter
@ToString
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
}
