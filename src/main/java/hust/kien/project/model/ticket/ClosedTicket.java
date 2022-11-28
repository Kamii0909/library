package hust.kien.project.model.ticket;

import java.time.LocalDate;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
@SuperBuilder
public class ClosedTicket extends Ticket {
    
    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;
}
