package hust.kien.project.model.book;

import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(region = "Book Genre", usage = CacheConcurrencyStrategy.READ_WRITE)
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
