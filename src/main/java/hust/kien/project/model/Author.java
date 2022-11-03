package hust.kien.project.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

@Entity
public class Author {
    @Id
    @GeneratedValue
    private int id;

    private AuthorInfo authorInfo;

    public Author() {
    }

    public Author(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int authorId) {
        this.id = authorId;
    }
}
