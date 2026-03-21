package hust.kien.project.core.author;

import hust.kien.project.core.LocalDateAsStringType;
import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.Objects;

import static hust.kien.project.core.Utils.idRequired;

@Entity(name = "Author")
@Table(name = "Author")
class Author implements ReadonlyAuthor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;

    private String name;

    @JavaType(LocalDateAsStringType.class)
    @Column(name = "date_of_birth")
    private @Nullable LocalDate dateOfBirth;

    @Deprecated
    @SuppressWarnings("activeClients")
    protected Author() {
        // For Hibernate
    }

    Author(String name, @Nullable LocalDate dob) {
        this.name = name;
        this.dateOfBirth = dob;
    }


    @SuppressWarnings("null")
    public Author(String name, int age) {
        this.name = name;
        this.dateOfBirth = LocalDate.now().minusYears(age);
    }

    void setName(String name) {
        this.name = name;
    }

    void setDateOfBirth(@Nullable LocalDate dob) {
        this.dateOfBirth = dob;
    }

    @Override
    public AuthorId id() {
        return new AuthorId(idRequired(id));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public @Nullable LocalDate dateOfBirth() {
        return dateOfBirth;
    }
}
