package hust.kien.project.model.client;

import java.time.LocalDate;
import java.util.List;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Embeddable
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@NoArgsConstructor
public class ClientRentInfo {

    @Enumerated(EnumType.STRING)
    @ToString.Include
    private ClientTier clientTier;

    private Subscription subscription;

    @OneToMany(mappedBy = "client")
    private List<ActiveTicket> activeTickets;

    @OneToMany(mappedBy = "client")
    private List<ClosedTicket> closedTickets;

    @Builder
    public ClientRentInfo(ClientTier clientTier, LocalDate startDate, LocalDate endDate) {
        this.clientTier = clientTier;
        this.subscription = Subscription.builder()
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

}
