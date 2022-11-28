package hust.kien.project.model.client;

import java.util.List;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.model.ticket.ClosedTicket;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Embeddable
@Getter
@Setter
@ToString(onlyExplicitlyIncluded = true, includeFieldNames = false)
@RequiredArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ClientRentInfo {
    
    @Enumerated(EnumType.STRING)
    @NonNull
    @ToString.Include
    private ClientTier clientTier;

    private List<Integer> year;
    
    @OneToMany(mappedBy = "client")
    private List<ActiveTicket> activeTickets;
    
    @OneToMany(mappedBy = "client")
    private List<ClosedTicket> closedTickets;

}
