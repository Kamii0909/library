package hust.kien.project.core.client;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.hibernate.annotations.Nationalized;

import static hust.kien.project.core.Utils.idRequired;

@Entity
public class Client implements ReadonlyClient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nationalized
    private String name;

    @Nationalized
    private String address;

    private String phoneNumber;

    protected Client() {
        // Hibernate
    }

    public Client(String name, String address, String phoneNumber) {
        this.name = name;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    @Override
    public ClientId id() {
        return ClientId.of(idRequired(id));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public String address() {
        return address;
    }

    @Override
    public String phoneNumber() {
        return phoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
