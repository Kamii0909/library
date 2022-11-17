package hust.kien.project.model.author;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import jakarta.persistence.Cacheable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cacheable
@Cache(region = "author", usage = CacheConcurrencyStrategy.READ_WRITE)
public class Author {
    @Id
    @GeneratedValue
    private Long id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long authorId) {
        this.id = authorId;
    }
}
