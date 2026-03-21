package hust.kien.project.core.book.internal;

import hust.kien.project.core.LocalDateAsStringType;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.ReadonlyBook;
import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.JavaType;
import org.jspecify.annotations.Nullable;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static hust.kien.project.core.Utils.idRequired;

@Entity
class Book implements ReadonlyBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private @Nullable Long id;

    private String name;

    @JavaType(LocalDateAsStringType.class)
    private @Nullable LocalDate releasedDate;

    @ElementCollection
    @CollectionTable(name = "Book_BookGenre", joinColumns = @JoinColumn(name = "book_id"))
    @Column(name = "genre")
    @Fetch(FetchMode.SUBSELECT)
    private List<String> genres;

    @SuppressWarnings("null")
    protected Book() {
        // Hibernate
    }

    @SuppressWarnings("null")
    Book(String name, @Nullable LocalDate releasedDate, List<String> genres) {
        this.name = name;
        this.releasedDate = releasedDate;
        this.genres = genres;
    }

    @Override
    public BookId id() {
        return BookId.of(idRequired(id));
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public @Nullable LocalDate releasedDate() {
        return releasedDate;
    }

    public List<String> genres() {
        return Collections.unmodifiableList(genres);
    }

    void setName(String name) {
        this.name = name;
    }

    void setReleasedDate(@Nullable LocalDate releasedDate) {
        this.releasedDate = releasedDate;
    }

}
