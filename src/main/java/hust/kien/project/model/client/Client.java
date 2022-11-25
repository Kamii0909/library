package hust.kien.project.model.client;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.domain.Persistable;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Client implements Persistable<Long> {
    @Id
    @GeneratedValue
    private Long id;

    private ClientContactInfo contactInfo;

    private ClientRentInfo rentInfo;

    public Client(ClientContactInfo contactInfo, ClientTier clientTier) {
        this.contactInfo = contactInfo;
        this.rentInfo = new ClientRentInfo(clientTier);
    }

    public Client() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ClientContactInfo getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(ClientContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public ClientRentInfo getRentInfo() {
        return rentInfo;
    }

    public void setRentInfo(ClientRentInfo rentInfo) {
        this.rentInfo = rentInfo;
    }

    @Override
    public boolean isNew() {
        // TODO Auto-generated method stub
        return false;
    }


}
