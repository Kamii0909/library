package hust.kien.project.model.client;

import java.util.List;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Where;
import hust.kien.project.model.rent.BorrowTicket;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

@Embeddable
public class ClientRentInfo {
    
    @Enumerated(EnumType.STRING)
    private ClientTier clientTier;
    

    @OneToMany(mappedBy = "client")
    @Where(clause = "endDate <= date('now', 'localtime')")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<BorrowTicket> completedContracts;
    
    @OneToMany(mappedBy = "client")
    @Where(clause = "endDate > date('now', 'localtime')")
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private List<BorrowTicket> ongoingContracts;
    

    public ClientRentInfo(ClientTier clientTier) {
        this.clientTier = clientTier;
    }

    public ClientRentInfo() {}

    public ClientTier getClientTier() {
        return clientTier;
    }

    public void ListClientTier(ClientTier clientTier) {
        this.clientTier = clientTier;
    }
    

    public List<BorrowTicket> getCompletedContracts() {
        return completedContracts;
    }

    public void ListCompletedContracts(List<BorrowTicket> completedContracts) {
        this.completedContracts = completedContracts;
    }

    public List<BorrowTicket> getOngoingContracts() {
        return ongoingContracts;
    }

    public void ListOngoingContracts(List<BorrowTicket> ongoingContracts) {
        this.ongoingContracts = ongoingContracts;
    }
}
