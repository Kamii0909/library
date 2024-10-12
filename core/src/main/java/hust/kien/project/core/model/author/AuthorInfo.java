package hust.kien.project.core.model.author;

import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

import java.util.HashSet;
import java.util.Set;

import hust.kien.project.core.model.book.Book;

@Embeddable
public class AuthorInfo {
    private String name;
    private int age;
    @ManyToMany(mappedBy = "bookInfo.authors")
    private Set<Book> books;

    @SuppressWarnings("all")
    private static Set<Book> $default$books() {
        return new HashSet<>();
    }


    @SuppressWarnings("all")
    public static abstract class AuthorInfoBuilder<C extends AuthorInfo, B extends AuthorInfoBuilder<C, B>> {
        @SuppressWarnings("all")
        private String name;
        @SuppressWarnings("all")
        private int age;
        @SuppressWarnings("all")
        private boolean books$set;
        @SuppressWarnings("all")
        private Set<Book> books$value;

        @SuppressWarnings("all")
        protected abstract B self();

        @SuppressWarnings("all")
        public abstract C build();

        /**
         * @return {@code this}.
         */
        @SuppressWarnings("all")
        public B name(final String name) {
            this.name = name;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @SuppressWarnings("all")
        public B age(final int age) {
            this.age = age;
            return self();
        }

        /**
         * @return {@code this}.
         */
        @SuppressWarnings("all")
        public B books(final Set<Book> books) {
            this.books$value = books;
            books$set = true;
            return self();
        }

        @Override
        @SuppressWarnings("all")
        public String toString() {
            return "AuthorInfo.AuthorInfoBuilder(name=" + this.name + ", age=" + this.age + ", books$value=" + this.books$value + ")";
        }
    }


    @SuppressWarnings("all")
    private static final class AuthorInfoBuilderImpl extends AuthorInfoBuilder<AuthorInfo, AuthorInfoBuilderImpl> {
        @SuppressWarnings("all")
        private AuthorInfoBuilderImpl() {
        }

        @Override
        @SuppressWarnings("all")
        protected AuthorInfoBuilderImpl self() {
            return this;
        }

        @Override
        @SuppressWarnings("all")
        public AuthorInfo build() {
            return new AuthorInfo(this);
        }
    }

    @SuppressWarnings("all")
    protected AuthorInfo(final AuthorInfoBuilder<?, ?> b) {
        this.name = b.name;
        this.age = b.age;
        if (b.books$set) this.books = b.books$value;
         else this.books = AuthorInfo.$default$books();
    }

    @SuppressWarnings("all")
    public static AuthorInfoBuilder<?, ?> builder() {
        return new AuthorInfoBuilderImpl();
    }

    @SuppressWarnings("all")
    public String getName() {
        return this.name;
    }

    @SuppressWarnings("all")
    public int getAge() {
        return this.age;
    }

    @SuppressWarnings("all")
    public Set<Book> getBooks() {
        return this.books;
    }

    @SuppressWarnings("all")
    public void setName(final String name) {
        this.name = name;
    }

    @SuppressWarnings("all")
    public void setAge(final int age) {
        this.age = age;
    }

    @SuppressWarnings("all")
    public void setBooks(final Set<Book> books) {
        this.books = books;
    }

    @Override
    @SuppressWarnings("all")
    public String toString() {
        return "AuthorInfo(" + this.getName() + ", " + this.getAge() + ")";
    }

    @SuppressWarnings("all")
    public AuthorInfo() {
    }
}
