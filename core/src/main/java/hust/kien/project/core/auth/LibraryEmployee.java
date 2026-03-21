package hust.kien.project.core.auth;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class LibraryEmployee {
    @Id
    private String username;
    private byte[] salt;
    private byte[] encryptedPassword;
    private String employeeName;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private List<LibraryRole> roles;

    protected LibraryEmployee() {
        // For Hibernate
    }

    public LibraryEmployee(String username, String employeeName, List<LibraryRole> roles) {
        this.username = username;
        this.employeeName = employeeName;
        this.roles = roles;
    }


    public String getUsername() {
        return this.username;
    }

    public byte[] getSalt() {
        return this.salt;
    }

    public byte[] getEncryptedPassword() {
        return this.encryptedPassword;
    }

    public String getEmployeeName() {
        return this.employeeName;
    }

    public List<LibraryRole> getRoles() {
        return this.roles;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public void setSalt(final byte[] salt) {
        this.salt = salt;
    }

    public void setEncryptedPassword(final byte[] encryptedPassword) {
        this.encryptedPassword = encryptedPassword;
    }

    public void setEmployeeName(final String employeeName) {
        this.employeeName = employeeName;
    }

    public void setRoles(final List<LibraryRole> roles) {
        this.roles = roles;
    }
}
