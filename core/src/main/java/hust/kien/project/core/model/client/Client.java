package hust.kien.project.core.model.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

import java.time.LocalDate;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;

@Entity
public class Client implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;
    private ClientContactInfo contactInfo;
    private ClientRentInfo rentInfo;

    public Client(String name,
        String address,
        ClientTier tier,
        LocalDate startDate,
        LocalDate endDate) {

        this.contactInfo = ClientContactInfo
            .builder()
            .name(name)
            .address(address)
            .build();
        this.rentInfo = ClientRentInfo.builder()
            .clientTier(tier)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }


    public static class ClientBuilder {
        private String name;
        private String address;
        private ClientTier tier;
        private LocalDate startDate;
        private LocalDate endDate;

        ClientBuilder() {}

        public ClientBuilder name(final String name) {
            this.name = name;
            return this;
        }

        public ClientBuilder address(final String address) {
            this.address = address;
            return this;
        }

        public ClientBuilder tier(final ClientTier tier) {
            this.tier = tier;
            return this;
        }

        public ClientBuilder startDate(final LocalDate startDate) {
            this.startDate = startDate;
            return this;
        }

        public ClientBuilder endDate(final LocalDate endDate) {
            this.endDate = endDate;
            return this;
        }

        public Client build() {
            return new Client(this.name, this.address, this.tier, this.startDate, this.endDate);
        }
    }

    public static ClientBuilder builder() {
        return new ClientBuilder();
    }

    public Long getId() {
        return this.id;
    }

    public ClientContactInfo getContactInfo() {
        return this.contactInfo;
    }

    public ClientRentInfo getRentInfo() {
        return this.rentInfo;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public void setContactInfo(final ClientContactInfo contactInfo) {
        this.contactInfo = contactInfo;
    }

    public void setRentInfo(final ClientRentInfo rentInfo) {
        this.rentInfo = rentInfo;
    }

    @Override
    public String toString() {
        return "Client(" + this.getId() + ", " + this.getContactInfo() + ", " + this.getRentInfo()
            + ")";
    }

    public Client() {}
}
