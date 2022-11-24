package hust.kien.project.service.dynamic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public abstract class GeneralLibrarySpecificationBuilder<T> {
    protected List<Specification<T>> specList;
    
    public GeneralLibrarySpecificationBuilder() {
        specList = new ArrayList<>();
    }

    public Specification<T> build() {
        return Specification.allOf(specList);
    }


    public abstract GeneralLibrarySpecificationBuilder<T> initCollection();
}
