package hust.kien.project.model.rent;

import java.time.LocalDate;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ClosedTicket extends Ticket {
    
    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;
}
