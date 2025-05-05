package hust.kien.project.core.service.dynamic;

import java.util.Collection;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.Author_;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.book.BookGenre;
import hust.kien.project.core.model.book.BookGenre_;
import hust.kien.project.core.model.book.BookInfo_;
import hust.kien.project.core.model.book.BookStock_;
import hust.kien.project.core.model.book.Book_;
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

    public BookSpecificationBuilder reimburseCostBetween(double from, double to) {
        specList.add((root, cq, cb) -> cb
            .between(root.get(Book_.bookStock).get(BookStock_.reimburseCost), from, to));
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

    public BookSpecificationBuilder fromAllAuthorWithNameLike(String string) {
        specList.add((root, cq, cb) -> cb.like(root.join(Book_.bookInfo).join(BookInfo_.authors)
            .get(Author_.name), "%" + string + "%"));
        return this;
    }

    public BookSpecificationBuilder fromAllAuthorsWithNameLike(Collection<String> strings) {
        for (String string : strings) {
            fromAllAuthorWithNameLike(string);
        }
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

    public BookSpecificationBuilder withAtLeastOneGenreLike(String genre) {
        specList.add((root, cq, cb) -> cb.like(
            root.join(Book_.bookInfo).join(BookInfo_.bookGenres).get(BookGenre_.name),
            "%" + genre + "%"));
        return this;
    }

    public BookSpecificationBuilder withEachGenreLikeAtLeastOne(Iterable<String> genres) {
        for (String string : genres) {
            withAtLeastOneGenreLike(string);
        }
        return this;
    }

    public BookSpecificationBuilder withAtLeastOneOfGenre(Collection<BookGenre> genres) {
        specList
            .add((root, cq, cb) -> root.join(Book_.bookInfo).join(BookInfo_.authors).in(genres));
        return this;
    }

    @Override
    public Class<Book> libraryType() {
        return Book.class;
    }

    @Override
    public BookCollectionInitBuilder initCollection() {
        return new BookCollectionInitBuilder();
    }

    public class BookCollectionInitBuilder extends LibraryCollectionInitBuilder<Book> {

        public BookCollectionInitBuilder authors() {
            specList.add((root, cq, cb) -> {
                root.fetch(Book_.bookInfo).fetch(BookInfo_.authors, JoinType.LEFT);
                return null;
            });
            return this;
        }

        public BookCollectionInitBuilder genres() {
            specList.add((root, cq, cb) -> {
                root.fetch(Book_.bookInfo).fetch(BookInfo_.bookGenres, JoinType.LEFT);
                return null;
            });
            return this;
        }

        public BookCollectionInitBuilder ongoingContracts() {
            specList.add((root, cq, cb) -> {
                root.fetch(Book_.bookStock).fetch(BookStock_.ongoingContracts, JoinType.LEFT);
                return null;
            });
            return this;
        }

        public BookCollectionInitBuilder completedContracts() {
            specList.add((root, cq, cb) -> {
                root.fetch(Book_.bookStock).fetch(BookStock_.completedContracts, JoinType.LEFT);
                return null;
            });
            return this;
        }

        @Override
        public BookSpecificationBuilder back() {
            return BookSpecificationBuilder.this;
        }
    }

    @Override
    public BookSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(Book_.id), id));
        return this;
    }

}
