package hust.kien.project.model.auth;

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

    public LibraryEmployee(String username, String employeeName, List<LibraryRole> roles) {
        this.username = username;
        this.employeeName = employeeName;
        this.roles = roles;
    }

    public static class LibraryEmployeeBuilder {
        private String username;
        private String employeeName;
        private List<LibraryRole> roles;

        LibraryEmployeeBuilder() {}

        public LibraryEmployeeBuilder username(final String username) {
            this.username = username;
            return this;
        }

        public LibraryEmployeeBuilder employeeName(final String employeeName) {
            this.employeeName = employeeName;
            return this;
        }

        public LibraryEmployeeBuilder roles(final List<LibraryRole> roles) {
            this.roles = roles;
            return this;
        }

        public LibraryEmployee build() {
            return new LibraryEmployee(this.username, this.employeeName, this.roles);
        }

        @Override
        public String toString() {
            return "LibraryEmployee.LibraryEmployeeBuilder(username=" + this.username
                + ", employeeName=" + this.employeeName + ", roles=" + this.roles + ")";
        }
    }

    public static LibraryEmployeeBuilder builder() {
        return new LibraryEmployeeBuilder();
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

    public LibraryEmployee() {}

    @Override
    public String toString() {
        return "LibraryEmployee(username=" + this.getUsername() + ", salt="
            + java.util.Arrays.toString(this.getSalt()) + ", encryptedPassword="
            + java.util.Arrays.toString(this.getEncryptedPassword()) + ", employeeName="
            + this.getEmployeeName() + ", roles=" + this.getRoles() + ")";
    }
}
