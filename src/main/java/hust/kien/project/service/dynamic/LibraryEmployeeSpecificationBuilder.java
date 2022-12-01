package hust.kien.project.service.dynamic;

import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryEmployee_;

public class LibraryEmployeeSpecificationBuilder extends GeneralLibrarySpecificationBuilder<LibraryEmployee> {

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
    @Deprecated
    public LibraryCollectionInitBuilder<LibraryEmployee> initCollection() {
        return new LibraryEmployeeCollectionInitBuilder();
    }

    public class LibraryEmployeeCollectionInitBuilder extends LibraryCollectionInitBuilder<LibraryEmployee> {

        @Override
        public GeneralLibrarySpecificationBuilder<LibraryEmployee> back() {
            return LibraryEmployeeSpecificationBuilder.this;
        }

    }
    
}
