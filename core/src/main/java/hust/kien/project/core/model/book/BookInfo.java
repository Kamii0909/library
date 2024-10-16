package hust.kien.project.core.model.book;

import java.util.HashSet;
import java.util.Set;

import hust.kien.project.core.model.author.Author;
import jakarta.persistence.Embeddable;
import jakarta.persistence.ManyToMany;

@Embeddable
public class BookInfo {
    private String name;
    private int releasedYear;
    @ManyToMany
    private Set<BookGenre> bookGenres;
    @ManyToMany
    private Set<Author> authors;

    public BookInfo(String bookName, int releasedYear) {
        this.name = bookName;
        this.releasedYear = releasedYear;
    }

    private static Set<BookGenre> $default$bookGenres() {
        return new HashSet<>();
    }

    private static Set<Author> $default$authors() {
        return new HashSet<>();
    }


    @SuppressWarnings("all")
    public abstract static class BookInfoBuilder<C extends BookInfo, B extends BookInfoBuilder<C, B>> {
        private String name;
        private int releasedYear;
        private boolean bookGenres$set;
        private Set<BookGenre> bookGenres$value;
        private boolean authors$set;
        private Set<Author> authors$value;

        protected abstract B self();

        public abstract C build();

        public B name(final String name) {
            this.name = name;
            return self();
        }

        public B releasedYear(final int releasedYear) {
            this.releasedYear = releasedYear;
            return self();
        }

        public B bookGenres(final Set<BookGenre> bookGenres) {
            this.bookGenres$value = bookGenres;
            bookGenres$set = true;
            return self();
        }

        public B authors(final Set<Author> authors) {
            this.authors$value = authors;
            authors$set = true;
            return self();
        }

        @Override
        public String toString() {
            return "BookInfo.BookInfoBuilder(name=" + this.name + ", releasedYear="
                + this.releasedYear + ", bookGenres$value=" + this.bookGenres$value
                + ", authors$value=" + this.authors$value + ")";
        }
    }


    private static final class BookInfoBuilderImpl
        extends BookInfoBuilder<BookInfo, BookInfoBuilderImpl> {
        private BookInfoBuilderImpl() {}

        @Override
        protected BookInfoBuilderImpl self() {
            return this;
        }

        @Override
        public BookInfo build() {
            return new BookInfo(this);
        }
    }

    protected BookInfo(final BookInfoBuilder<?, ?> b) {
        this.name = b.name;
        this.releasedYear = b.releasedYear;
        if (b.bookGenres$set)
            this.bookGenres = b.bookGenres$value;
        else
            this.bookGenres = BookInfo.$default$bookGenres();
        if (b.authors$set)
            this.authors = b.authors$value;
        else
            this.authors = BookInfo.$default$authors();
    }

    @SuppressWarnings("all")
    public static BookInfoBuilder<?, ?> builder() {
        return new BookInfoBuilderImpl();
    }

    public String getName() {
        return this.name;
    }

    public int getReleasedYear() {
        return this.releasedYear;
    }

    public Set<BookGenre> getBookGenres() {
        return this.bookGenres;
    }

    public Set<Author> getAuthors() {
        return this.authors;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setReleasedYear(final int releasedYear) {
        this.releasedYear = releasedYear;
    }

    public void setBookGenres(final Set<BookGenre> bookGenres) {
        this.bookGenres = bookGenres;
    }

    public void setAuthors(final Set<Author> authors) {
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "BookInfo(name=" + this.getName() + ", releasedYear=" + this.getReleasedYear() + ")";
    }

    public BookInfo() {}
}
