package hust.kien.project.model.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class BookGenre {
    @Id
    private String name;

    public BookGenre(String name){
        this.name = name;
    }

    public BookGenre() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
