// Generated by delombok at Sun Feb 19 21:29:28 ICT 2023
package hust.kien.project.model.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BookGenre {
    @Id
    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "BookGenre(" + this.getName() + ")";
    }

    public BookGenre() {}

    public BookGenre(final String name) {
        this.name = name;
    }
}
