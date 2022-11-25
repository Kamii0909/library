package hust.kien.project.service.dynamic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public abstract class GeneralLibrarySpecificationBuilder<T> {

    protected List<Specification<T>> specList;

    public abstract GeneralLibrarySpecificationBuilder<T> withId(Object id);

    public GeneralLibrarySpecificationBuilder() {
        specList = new ArrayList<>();
    }

    public Specification<T> build() {
        return Specification.allOf(specList);
    }

    /**
     * Fuck you type erasure
     */
    public abstract Class<T> libraryType();

    public abstract LibraryCollectionInitBuilder<T> initCollection();
}
