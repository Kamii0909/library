package hust.kien.project.model.rent;

import java.time.LocalDate;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import jakarta.persistence.Convert;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@SuperBuilder
public abstract class Ticket {
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
