package hust.kien.project.core.service.dynamic;

import java.time.LocalDate;
import java.util.Collection;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.Author_;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.model.book.Book;
import jakarta.persistence.criteria.JoinType;

public class AuthorSpecificationBuilder extends GeneralLibrarySpecificationBuilder<Author> {

    public AuthorSpecificationBuilder nameContains(String name) {
        specList.add((root, _, cb) -> cb.like(root.get(Author_.name), "%" + name + "%"));
        return this;
    }

    public AuthorSpecificationBuilder ageBetween(int from, int to) {
        LocalDate now = LocalDate.now();
        LocalDate fromDate = now.minusYears(from);
        LocalDate toDate = now.minusYears(to);
        specList.add((root, _, cb) -> cb.between(root.get(Author_.dateOfBirth), fromDate, toDate));
        return this;
    }

    public AuthorSpecificationBuilder wroteAtLeastOneOf(Collection<Book> books) {
        specList.add((root, _, _) -> root.join(Author_.books).in(books));
        return this;
    }

    public AuthorSpecificationBuilder wroteAllOf(Iterable<Book> books) {
        for (Book book : books) {
            wroteBook(book);
        }
        return this;
    }

    public AuthorSpecificationBuilder wroteBook(Book book) {
        specList.add((root, _, cb) -> cb.isMember(new BookId(book.getId()), root.get(Author_.books)));
        return this;
    }

    @Override
    public Class<Author> libraryType() {
        return Author.class;
    }

    @Override
    public AuthorCollectionInitBuilder initCollection() {
        return new AuthorCollectionInitBuilder();
    }

    public class AuthorCollectionInitBuilder extends LibraryCollectionInitBuilder<Author> {

        public AuthorCollectionInitBuilder books() {
            specList.add((root, _, _) -> {
                root.fetch(Author_.books, JoinType.LEFT);
                return null;
            });
            return this;
        }

        @Override
        public AuthorSpecificationBuilder back() {
            return AuthorSpecificationBuilder.this;
        }
    }

    @Override
    public AuthorSpecificationBuilder withId(Object id) {
        specList.add((root, _, cb) -> cb.equal(root.get(Author_.id), id));
        return this;
    }

}
