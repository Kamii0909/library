package hust.kien.project.service.dynamic;

import org.springframework.data.jpa.domain.Specification;


public interface GeneralSpecificationBuilder<T> {

    Specification<T> build();

    /**
     * Fuck you type erasure
     */
    Class<T> libraryType();

    LibraryCollectionInitBuilder<T> initCollection();

}
