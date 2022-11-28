package hust.kien.project.model.client;

import java.time.LocalDate;
import hust.kien.project.model.ticket.LocalDateConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class Subscription {
    
    @Convert(converter = LocalDateConverter.class)
    private LocalDate startDate;

    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;
}
