package hust.kien.project.service;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;


public class BookSpecificationBuilder {

    private List<Specification<Book>> specList;

    public BookSpecificationBuilder() {
        specList = new ArrayList<>();
    }

    public BookSpecificationBuilder nameContains(String name) {
        specList.add((root, cq, cb) -> cb.like(root.get(Book_.bookInfo).get(BookInfo_.name),
            "%" + name + "%"));
        return this;
    }

    public BookSpecificationBuilder releasedBetween(int from, int to) {
        specList.add((root, cq, cb) -> cb
            .between(root.get(Book_.bookInfo).get(BookInfo_.releasedYear), from, to));
        return this;
    }

    public BookSpecificationBuilder stockBetween(int from, int to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Book_.bookStock).get(BookStock_.stock),
            from, to));
        return this;
    }

    public BookSpecificationBuilder reimburseCoseBetween(int from, int to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Book_.bookStock).get(BookStock_.stock),
            from, to));
        return this;
    }

    public Specification<Book> build() {
        return Specification.allOf(specList);
    }

}
