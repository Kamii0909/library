package hust.kien.project.core.service.dynamic;

import hust.kien.project.core.model.auth.LibraryEmployee;
import hust.kien.project.core.model.auth.LibraryEmployee_;

public class LibraryEmployeeSpecificationBuilder extends
    GeneralLibrarySpecificationBuilder<LibraryEmployee> {

    public LibraryEmployeeSpecificationBuilder withUsername(String username) {
        specList.add((root, cq, cb) -> cb.equal(root.get(LibraryEmployee_.username), username));
        return this;
    }

    @Override
    public LibraryEmployeeSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(LibraryEmployee_.username), id));
        return this;
    }

    @Override
    public Class<LibraryEmployee> libraryType() {
        return LibraryEmployee.class;
    }

    @Override
    public LibraryEmployeeSpecificationBuilder.LibraryEmployeeCollectionInitBuilder initCollection() {
        return new LibraryEmployeeCollectionInitBuilder();
    }

    public class LibraryEmployeeCollectionInitBuilder
        extends LibraryCollectionInitBuilder<LibraryEmployee> {

        public LibraryEmployeeCollectionInitBuilder roles() {
            specList.add((root, cq, cb) -> {
                root.fetch(LibraryEmployee_.roles);
                return null;
            });
            return this;
        }

        @Override
        public GeneralSpecificationBuilder<LibraryEmployee> back() {
            return LibraryEmployeeSpecificationBuilder.this;
        }

    }

}
