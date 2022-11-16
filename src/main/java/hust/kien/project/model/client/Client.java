package hust.kien.project.model.client;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(region = "book", usage = CacheConcurrencyStrategy.READ_WRITE)
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


}
