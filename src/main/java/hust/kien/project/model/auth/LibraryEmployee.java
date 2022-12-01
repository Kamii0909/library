package hust.kien.project.model.auth;

import java.util.List;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LibraryEmployee {
    @Id
    private String username;

    private byte[] salt;

    private byte[] encryptedPassword;

    private String employeeName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<LibraryRole> roles;

    @Builder
    public LibraryEmployee(String username, String employeeName, List<LibraryRole> roles) {
        this.username = username;
        this.employeeName = employeeName;
        this.roles = roles;
    }
}
