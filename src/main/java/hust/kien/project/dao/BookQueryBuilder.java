package hust.kien.project.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.Author_;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.SetJoin;

public class BookQueryBuilder extends LibraryCriteriaBuilder<Book> {

    private List<Predicate> predicates;

    public BookQueryBuilder(CriteriaBuilder cb) {
        super(Book.class, cb);
        predicates = new ArrayList<>();
    }

    public BookQueryBuilder nameExact(String name) {
        predicates.add(cb.equal(root.get(Book_.bookInfo).get(BookInfo_.bookName), name));
        return this;
    }

    public BookQueryBuilder nameContain(String name) {
        predicates.add(cb.like(root.get(Book_.bookInfo).get(BookInfo_.bookName), "%" + name + "%"));
        return this;
    }

    public BookQueryBuilder yearBetween(int from, int to) {
        predicates.add(cb.between(root.get(Book_.bookInfo).get(BookInfo_.releasedYear), from, to));
        return this;
    }

    public BookQueryBuilder stockBetween(int from, int to) {
        predicates.add(cb.between(root.get(Book_.bookStock).get(BookStock_.stock), from, to));
        return this;
    }

    public BookQueryBuilder reimburseCostBetween(double from, double to) {
        predicates
            .add(cb.between(root.get(Book_.bookStock).get(BookStock_.reimburseCost), from, to));
        return this;
    }

    public BookQueryBuilder byAuthor(Author author) {
        predicates.add(cb.isMember(author, root.get(Book_.bookInfo).get(BookInfo_.authors)));
        return this;
    }

    public BookQueryBuilder byAllAuthors(Collection<Author> authors) {
        for (Author author : authors) {
            byAuthor(author);
        }
        return this;
    }

    public BookQueryBuilder byAtLeastOneAuthor(Collection<Author> authors) {
        SetJoin<BookInfo, Author> join = root.join(Book_.bookInfo).join(BookInfo_.authors);

        predicates.add(join.in(authors));

        return this;
    }

    public BookQueryBuilder initializeCollections() {
        root.fetch(Book_.bookInfo).fetch(BookInfo_.authors);
        root.fetch(Book_.bookInfo).fetch(BookInfo_.bookGenres);

        return this;
    }

    public CriteriaQuery<Book> build() {
        if(predicates.size() == 0) {
            return getQuery();
        }
        return where(predicates.toArray(Predicate[]::new)).getQuery();
    }

}
