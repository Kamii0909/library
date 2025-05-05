package hust.kien.project.core.model.client;

import java.time.LocalDate;
import java.util.List;

import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ActiveTicket_;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.model.ticket.ClosedTicket_;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

@Embeddable
public class ClientRentInfo {
    @Enumerated(EnumType.STRING)
    private ClientTier clientTier;
    
    @Embedded
    private Subscription subscription;
    
    @OneToMany(mappedBy = ActiveTicket_.CLIENT)
    private List<ActiveTicket> activeTickets;
    
    @OneToMany(mappedBy = ClosedTicket_.CLIENT)
    private List<ClosedTicket> closedTickets;
    
    public ClientRentInfo(ClientTier clientTier, LocalDate startDate, LocalDate endDate) {
        this.clientTier = clientTier;
        this.subscription = Subscription.builder().startDate(startDate).endDate(endDate).build();
    }
    
    public static class ClientRentInfoBuilder {
        private ClientTier clientTier;
        
        private LocalDate startDate;
        
        private LocalDate endDate;
        
        ClientRentInfoBuilder() {
        }
        
        public ClientRentInfoBuilder clientTier(final ClientTier clientTier) {
            this.clientTier = clientTier;
            return this;
        }
        
        public ClientRentInfoBuilder startDate(final LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }
        
        public ClientRentInfoBuilder endDate(final LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }
        
        public ClientRentInfo build() {
            return new ClientRentInfo(this.clientTier, this.startDate, this.endDate);
        }
    }
    
    public static ClientRentInfoBuilder builder() {
        return new ClientRentInfoBuilder();
    }
    
    public ClientTier getClientTier() {
        return this.clientTier;
    }
    
    public Subscription getSubscription() {
        return this.subscription;
    }
    
    public List<ActiveTicket> getActiveTickets() {
        return this.activeTickets;
    }
    
    public List<ClosedTicket> getClosedTickets() {
        return this.closedTickets;
    }
    
    public void setClientTier(final ClientTier clientTier) {
        this.clientTier = clientTier;
    }
    
    public void setSubscription(final Subscription subscription) {
        this.subscription = subscription;
    }
    
    public void setActiveTickets(final List<ActiveTicket> activeTickets) {
        this.activeTickets = activeTickets;
    }
    
    public void setClosedTickets(final List<ClosedTicket> closedTickets) {
        this.closedTickets = closedTickets;
    }
    
    @Override
    public String toString() {
        return "ClientRentInfo(" + this.getClientTier() + ")";
    }
    
    public ClientRentInfo() {
    }
}
