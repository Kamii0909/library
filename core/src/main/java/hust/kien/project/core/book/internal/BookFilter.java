package hust.kien.project.core.book.internal;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.Author_;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.BookInfo;
import hust.kien.project.core.book.BookInfo_;
import hust.kien.project.core.book.BookStock_;
import hust.kien.project.core.model.book.BookGenre;
import hust.kien.project.core.model.book.BookGenre_;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public final class BookFilter {
    private List<Specification<Book>> specifications = new ArrayList<>();

    Specification<Book> build() {
        return Specification.allOf(specifications);
    }

    public BookFilter nameContains(String name) {
        specifications.add((root, _, cb) -> cb.like(root.get(Book_.name), "%" + name + "%"));
        return this;
    }

    public BookFilter releasedBetween(LocalDate from, LocalDate to) {
        specifications.add((root, _, cb) -> cb.between(root.get(Book_.releasedDate), from, to));
        return this;
    }

    public BookFilter stockBetween(int from, int to) {
        specifications.add((root, _, cb) -> cb.between(root.get(Book_.stock),
                from, to));
        return this;
    }

    public BookFilter reimburseCostBetween(double from, double to) {
        specifications.add((root, _, cb) -> cb
                .between(root.get(Book_.bookStock).get(BookStock_.reimburseCost), from, to));
        return this;
    }

    public BookFilter fromAllAuthorWithNameLike(String string) {
        specifications.add((root, cq, cb) -> {
            // where exists (
            // select 1
            // from Book_Author ba
            // join Authors au on ba.authors_id = au.id
            // and au.name like '%name%'
            // and ba.books_id = book.id
            // )
            //
            Subquery<Integer> subquery = cq.subquery(Integer.class);
            Root<@NonNull Author> author = subquery.from(Author.class);

            ListJoin<Author, BookId> bookAuthorsJoin = author.join(Author_.books);

            subquery.select(cb.literal(1))
                    .where(
                            cb.like(author.get(Author_.name), "%" + string + "%"),
                            cb.equal(bookAuthorsJoin, root.get(Book_.id)));

            return cb.exists(subquery);
        });
        return this;
    }

    public BookFilter fromAllAuthorsWithNameLike(Collection<String> strings) {
        for (String string : strings) {
            fromAllAuthorWithNameLike(string);
        }
        return this;
    }

    public BookFilter withAtLeastOneGenreLike(String genre) {
        specifications.add((root, cq, cb) -> {

            Subquery<@NonNull Long> subquery = cq.subquery(Long.class);
            Root<@NonNull Book> subqueryRoot = cq.from(Book.class);
            ListJoin<BookInfo, BookGenre> genres = subqueryRoot.join(Book_.bookInfo).join(BookInfo_.bookGenres);

            subquery.select(subqueryRoot.get(Book_.id))
                    .where(cb.like(genres.get(BookGenre_.name), "%" + genre + "%"));

            return cb.in(root.get(Book_.id)).value(subquery);
        });
        return this;
    }

    public BookFilter withEachGenreLikeAtLeastOne(Iterable<String> genres) {
        for (String string : genres) {
            withAtLeastOneGenreLike(string);
        }
        return this;
    }
}
