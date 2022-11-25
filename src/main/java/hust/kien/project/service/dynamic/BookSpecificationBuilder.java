package hust.kien.project.service.dynamic;

import java.util.Collection;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;
import jakarta.persistence.criteria.JoinType;


public class BookSpecificationBuilder extends GeneralLibrarySpecificationBuilder<Book> {

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

    public BookSpecificationBuilder reimburseCostBetween(int from, int to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Book_.bookStock).get(BookStock_.stock),
            from, to));
        return this;
    }

    public BookSpecificationBuilder fromAuthor(Author author) {
        specList.add(
            (root, cq, cb) -> cb.isMember(author, root.get(Book_.bookInfo).get(BookInfo_.authors)));
        return this;
    }

    public BookSpecificationBuilder fromAllAuthors(Iterable<Author> authors) {
        for (Author author : authors) {
            fromAuthor(author);
        }
        return this;
    }

    public BookSpecificationBuilder fromAtLeastOneAuthor(Collection<Author> authors) {
        specList
            .add((root, cq, cb) -> root.join(Book_.bookInfo).join(BookInfo_.authors).in(authors));
        return this;
    }

    public BookSpecificationBuilder withGenre(BookGenre genre) {
        specList.add((root, cq, cb) -> cb.isMember(genre,
            root.get(Book_.bookInfo).get(BookInfo_.bookGenres)));
        return this;
    }

    public BookSpecificationBuilder withAllGenres(Iterable<BookGenre> genre) {
        for (BookGenre bookGenre : genre) {
            withGenre(bookGenre);
        }
        return this;
    }

    public BookSpecificationBuilder withAtLeastOneOfGenre(Collection<BookGenre> genres) {
        specList
            .add((root, cq, cb) -> root.join(Book_.bookInfo).join(BookInfo_.authors).in(genres));
        return this;
    }

    @Override
    public BookSpecificationBuilder initCollection() {
        specList.add((root, cq, cb) -> {
            root.fetch(Book_.bookInfo).fetch(BookInfo_.authors, JoinType.LEFT);
            root.fetch(Book_.bookInfo).fetch(BookInfo_.bookGenres, JoinType.LEFT);
            return null;
        });
        return this;
    }

    @Override
    public Class<Book> libraryType() {
        return Book.class;
    }

}
