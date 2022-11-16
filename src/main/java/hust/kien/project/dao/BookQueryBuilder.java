package hust.kien.project.dao;

import java.util.ArrayList;
import java.util.List;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;

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
        predicates.add(cb.between(root.get(Book_.bookStock).get(BookStock_.reimburseCost), from, to));
        return this;
    }



    public CriteriaQuery<Book> getQuery() {
        return where(predicates.toArray(Predicate[]::new)).getQuery();
    }

}
