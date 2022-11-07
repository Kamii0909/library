package hust.kien.project.model.client;

import java.util.Set;
import org.hibernate.annotations.Where;
import hust.kien.project.model.rent.BookRentContract;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

@Embeddable
public class ClientRentInfo {
    
    @Enumerated(EnumType.STRING)
    private ClientTier clientTier;
    

    @OneToMany(mappedBy = "client")
    @Where(clause = "strftime('%s', 'now') - endDate/1000 > 0")
    private Set<BookRentContract> completedContracts;
    
    @OneToMany(mappedBy = "client")
    @Where(clause = "strftime('%s', 'now') - endDate/1000 <= 0")
    private Set<BookRentContract> ongoingContracts;
    

    
    public ClientRentInfo(ClientTier clientTier) {
        this.clientTier = clientTier;
    }

    public ClientRentInfo() {}

    public ClientTier getClientTier() {
        return clientTier;
    }

    public void setClientTier(ClientTier clientTier) {
        this.clientTier = clientTier;
    }
    

    public Set<BookRentContract> getCompletedContracts() {
        return completedContracts;
    }

    public void setCompletedContracts(Set<BookRentContract> completedContracts) {
        this.completedContracts = completedContracts;
    }

    public Set<BookRentContract> getOngoingContracts() {
        return ongoingContracts;
    }

    public void setOngoingContracts(Set<BookRentContract> ongoingContracts) {
        this.ongoingContracts = ongoingContracts;
    }
}
