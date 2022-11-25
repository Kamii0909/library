package hust.kien.project.model.client;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Getter
@Setter
@NoArgsConstructor
public class Client {
    @Id
    @GeneratedValue
    private Long id;

    private ClientContactInfo contactInfo;

    private ClientRentInfo rentInfo;

    public Client(ClientContactInfo contactInfo, ClientTier clientTier) {
        this.contactInfo = contactInfo;
        this.rentInfo = new ClientRentInfo(clientTier);
    }

}
