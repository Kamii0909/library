package hust.kien.project.model.rent;

import java.time.LocalDate;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.Inheritance;
import jakarta.persistence.InheritanceType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@SuperBuilder
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class ActiveTicket extends Ticket {
    
    @Convert(converter = LocalDateConverter.class)
    @Builder.Default
    private LocalDate endDate = LocalDateConverter.MAX;
}
