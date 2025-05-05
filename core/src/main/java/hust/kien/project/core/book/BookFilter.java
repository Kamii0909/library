package hust.kien.project.core.book;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.data.jpa.domain.Specification;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.Author_;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.book.BookGenre;
import hust.kien.project.core.model.book.BookGenre_;
import hust.kien.project.core.model.book.BookInfo;
import hust.kien.project.core.model.book.BookInfo_;
import hust.kien.project.core.model.book.BookStock_;
import hust.kien.project.core.model.book.Book_;
import jakarta.persistence.criteria.ListJoin;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public final class BookFilter {
    private List<Specification<Book>> specifications = new ArrayList<>();
    
    Specification<Book> build() {
        return Specification.allOf(specifications);
    }
    
    public BookFilter nameContains(String name) {
        specifications.add((root, _, cb) -> cb.like(
                root.get(Book_.bookInfo).get(BookInfo_.name),
                "%" + name + "%"));
        return this;
    }
    
    public BookFilter releasedBetween(int from, int to) {
        specifications.add((root, _, cb) -> cb
                .between(root.get(Book_.bookInfo).get(BookInfo_.releasedYear), from, to));
        return this;
    }
    
    public BookFilter stockBetween(int from, int to) {
        specifications.add((root, _, cb) -> cb.between(root.get(Book_.bookStock).get(BookStock_.stock),
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
            // where book.ids in (
            // select books_id
            // from Book_Author ba
            // join Authors au on ba.authors_id = au.id
            // and au.name like '%name%'
            // )
            //
            Subquery<@NonNull Long> subquery = cq.subquery(Long.class);
            Root<@NonNull Book> subqueryRoot = cq.from(Book.class);
            ListJoin<BookInfo, Author> authors = subqueryRoot.join(Book_.bookInfo).join(BookInfo_.authors);
            
            subquery.select(subqueryRoot.get(Book_.id))
                    .where(cb.like(authors.get(Author_.name), "%" + string + "%"));
            
            return cb.in(root.get(Book_.id)).value(subquery);
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
