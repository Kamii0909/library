package hust.kien.project.service.dynamic;

import java.util.Collection;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookGenre_;

public class BookGenreSpecificationBuilder extends GeneralLibrarySpecificationBuilder<BookGenre> {


    public BookGenreSpecificationBuilder nameLike(String name) {
        specList.add((root, cq, cb) -> cb.like(root.get(BookGenre_.name), "%" + name + "%"));
        return this;
    }

    public BookGenreSpecificationBuilder nameIn(Collection<String> names) {
        specList.add((root, cq, cb) -> root.get(BookGenre_.name).in(names));
        return this;
    }

    @Override
    public BookGenreCollectionInitBuilder initCollection() {
        return new BookGenreCollectionInitBuilder();
    }

    @Override
    public Class<BookGenre> libraryType() {
        return BookGenre.class;
    }

    @Override
    public BookGenreSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.like(root.get(BookGenre_.name), id.toString()));
        return this;
    }

    public class BookGenreCollectionInitBuilder extends LibraryCollectionInitBuilder<BookGenre> {

        @Override
        public BookGenreSpecificationBuilder back() {
            return BookGenreSpecificationBuilder.this;
        }

    }

}
