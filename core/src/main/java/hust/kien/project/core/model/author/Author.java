package hust.kien.project.core.model.author;

import hust.kien.project.core.model.LibraryLocatable;
import hust.kien.project.core.model.LibraryPersistable;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
public class Author implements LibraryLocatable, LibraryPersistable {
    @Id
    @GeneratedValue
    private Long id;
    private AuthorInfo authorInfo;

    public Author(AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    public Author(String name, int age) {
        this.authorInfo = AuthorInfo.builder().name(name).age(age).build();
    }


    @SuppressWarnings("all")
    public static class AuthorBuilder {
        @SuppressWarnings("all")
        private String name;
        @SuppressWarnings("all")
        private int age;

        @SuppressWarnings("all")
        AuthorBuilder() {}

        /**
         * @return {@code this}.
         */
        @SuppressWarnings("all")
        public AuthorBuilder name(final String name) {
            this.name = name;
            return this;
        }

        /**
         * @return {@code this}.
         */
        @SuppressWarnings("all")
        public AuthorBuilder age(final int age) {
            this.age = age;
            return this;
        }

        @SuppressWarnings("all")
        public Author build() {
            return new Author(this.name, this.age);
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "Author.AuthorBuilder(name=" + this.name + ", age=" + this.age + ")";
        }
    }

    @SuppressWarnings("all")
    public static AuthorBuilder builder() {
        return new AuthorBuilder();
    }

    @SuppressWarnings("all")
    public Long getId() {
        return this.id;
    }

    @SuppressWarnings("all")
    public AuthorInfo getAuthorInfo() {
        return this.authorInfo;
    }

    @SuppressWarnings("all")
    public void setId(final Long id) {
        this.id = id;
    }

    @SuppressWarnings("all")
    public void setAuthorInfo(final AuthorInfo authorInfo) {
        this.authorInfo = authorInfo;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "Author(" + this.getId() + ", " + this.getAuthorInfo() + ")";
    }

    @SuppressWarnings("all")
    public Author() {}

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Author other = (Author) obj;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        return true;
    }
}
