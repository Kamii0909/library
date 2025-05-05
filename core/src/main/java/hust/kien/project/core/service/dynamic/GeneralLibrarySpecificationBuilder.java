package hust.kien.project.core.service.dynamic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public abstract class  GeneralLibrarySpecificationBuilder<T> implements GeneralSpecificationBuilder<T> {

    protected List<Specification<T>> specList;

    public abstract GeneralSpecificationBuilder<T> withId(Object id);

    protected GeneralLibrarySpecificationBuilder() {
        specList = new ArrayList<>();
    }

    @Override
    public Specification<T> build() {
        return Specification.allOf(specList);
    }
}
