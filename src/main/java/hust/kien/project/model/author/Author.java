package hust.kien.project.model.author;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

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

    public AuthorInfo getAuthorInfo() {
        return authorInfo;
    }

    public void setAuthorInfo(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public int getId() {
        return id;
    }

    public void setId(int authorId) {
        this.id = authorId;
    }
}
