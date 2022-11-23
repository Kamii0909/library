package hust.kien.project.service.dynamic;

import java.util.ArrayList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public abstract class GeneralLibrarySpecificationBuilder<T> {
    private boolean initCollections;
    protected List<Specification<T>> specList;
    
    public GeneralLibrarySpecificationBuilder() {
        specList = new ArrayList<>();
        initCollections = false;
    }

    public Specification<T> build() {
        return Specification.allOf(specList);
    }

    public boolean isCollectionInit() {
        return this.initCollections;
    }

    protected void doSetInitCollections(boolean initCollections) {
        this.initCollections = initCollections;
    }

    public abstract GeneralLibrarySpecificationBuilder<T> setInitCollections(boolean initCollections);
    

}
