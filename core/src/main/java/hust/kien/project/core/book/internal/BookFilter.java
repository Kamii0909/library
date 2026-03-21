package hust.kien.project.core.book.internal;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.bookauthor.BookAuthor;
import hust.kien.project.core.bookauthor.BookAuthorLink_;
import hust.kien.project.core.bookauthor.BookAuthor_;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.ParameterExpression;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("UnusedReturnValue")
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
    //
    //    public BookFilter stockBetween(int from, int to) {
    //        specifications.add((root, _, cb) -> cb.between(root.get(Book_.stock),
    //                from, to));
    //        return this;
    //    }
    //
    //    public BookFilter reimburseCostBetween(double from, double to) {
    //        specifications.add((root, _, cb) -> cb
    //                .between(root.get(Book_.bookStock).get(BookStock_.reimburseCost), from, to));
    //        return this;
    //    }

    public BookFilter fromAuthors(Iterable<AuthorId> authorIds) {
        specifications.add((root, cq, cb) -> {

            Subquery<Long> subquery = cq.subquery(Long.class);
            Root<BookAuthor> subqueryRoot = subquery.from(BookAuthor_.class_);
            subquery.select(subqueryRoot.get(BookAuthor_.id).get(BookAuthorLink_.bookId));

            return root.get(Book_.id).in(subquery);
        });
        return this;
    }

    public BookFilter withAtLeastOneGenreLike(String genre) {
        specifications.add((root, cq, cb) -> {

            // from Book B where B.id exists (select 1 from Book_Genre G where G.book_id = ? and genre like ?)
            Subquery<Integer> subquery = cq.subquery(Integer.class);

            Root<Book> subqueryRoot = subquery.correlate(root);
            subquery.select(cb.literal(1));

            ListJoin<Book, String> join = subqueryRoot.join(Book_.genres);
            subquery.where(cb.like(join, "%" + genre + "%"));

            return cb.exists(subquery);
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
