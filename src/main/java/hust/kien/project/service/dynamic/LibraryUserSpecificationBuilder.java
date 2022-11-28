package hust.kien.project.service.dynamic;

import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryUser_;

public class LibraryUserSpecificationBuilder extends GeneralLibrarySpecificationBuilder<LibraryEmployee> {

    public LibraryUserSpecificationBuilder employeeNameContains(String name) {
        specList.add((root, cq, cb) -> cb.like(root.get(LibraryUser_.employeeName), "%" + name + "%"));
        return this;
    }

    @Override
    public LibraryUserSpecificationBuilder withId(Object id) {
        specList.add((root, cq, cb) -> cb.equal(root.get(LibraryUser_.username), id));
        return this;
    }

    @Override
    public Class<LibraryEmployee> libraryType() {
        return LibraryEmployee.class;
    }

    @Override
    @Deprecated
    public LibraryCollectionInitBuilder<LibraryEmployee> initCollection() {
        return new UserCollectionInitBuilder();
    }

    public class UserCollectionInitBuilder extends LibraryCollectionInitBuilder<LibraryEmployee> {

        @Override
        public GeneralLibrarySpecificationBuilder<LibraryEmployee> back() {
            return LibraryUserSpecificationBuilder.this;
        }

    }
    
}
