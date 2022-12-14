package hust.kien.project.model.client;

import java.time.LocalDate;
import hust.kien.project.model.LibraryLocatable;
import hust.kien.project.model.LibraryPersistable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString(includeFieldNames = false)
@NoArgsConstructor
public class Client implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;

    private ClientContactInfo contactInfo;

    private ClientRentInfo rentInfo;

    @Builder
    public Client(String name, String address, ClientTier tier, LocalDate startDate, LocalDate endDate) {
        this.contactInfo = ClientContactInfo.builder()
            .name(name)
            .address(address)
            .build();
        this.rentInfo = ClientRentInfo.builder()
            .clientTier(tier)
            .startDate(startDate)
            .endDate(endDate)
            .build();
    }

}
