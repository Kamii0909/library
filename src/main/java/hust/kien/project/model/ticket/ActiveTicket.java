package hust.kien.project.model.ticket;

import java.time.LocalDate;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Builder;
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
public class ActiveTicket extends Ticket {
    
    @Convert(converter = LocalDateConverter.class)
    @Builder.Default
    private LocalDate endDate = LocalDateConverter.MAX;
}
