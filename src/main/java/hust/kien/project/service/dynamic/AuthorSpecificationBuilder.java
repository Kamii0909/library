package hust.kien.project.service.dynamic;

import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo_;
import hust.kien.project.model.author.Author_;
import hust.kien.project.model.book.Book;

import java.util.Collection;

public class AuthorSpecificationBuilder extends GeneralLibrarySpecificationBuilder<Author> {

    public AuthorSpecificationBuilder nameContains(String name) {
        specList.add((root, cq, cb) -> cb.like(root.get(Author_.authorInfo).get(AuthorInfo_.name), "%" + name + "%"));
        return this;
    }

    public AuthorSpecificationBuilder ageBetween(int from, int to) {
        specList.add((root, cq, cb) -> cb.between(root.get(Author_.authorInfo).get(AuthorInfo_.age), from, to));
        return this;
    }

    public AuthorSpecificationBuilder wroteAtLeastOneOf(Collection<Book> books) {
        specList.add((root, cq, cb) -> root.join(Author_.authorInfo).join(AuthorInfo_.books).in(books));
        return this;
    }


    public AuthorSpecificationBuilder wroteAllOf(Iterable<Book> books) {
        for (Book book : books) {
            wroteBook(book);
        }
        return this;
    }

    public AuthorSpecificationBuilder wroteBook(Book book) {
        specList.add((root, cq, cb) -> cb.isMember(book, root.get(Author_.authorInfo).get(AuthorInfo_.books)));
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
            specList.add((root, cq, cb) -> {
                root.fetch(Author_.authorInfo).fetch(AuthorInfo_.books);
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
        specList.add((root, cq, cb) -> cb.equal(root.get(Author_.id), id));
        return this;
    }

}
