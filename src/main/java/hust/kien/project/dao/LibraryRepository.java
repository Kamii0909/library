package hust.kien.project.dao;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LibraryRepository<T, ID extends Serializable>
    extends JpaRepository<T, ID>, JpaSpecificationExecutor<T> {

    List<T> findAll(Specification<T> spec, long offset, int maxResults, Sort sort);

    List<T> findAll(Specification<T> spec, long offset, int maxResults);

    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException("Don't delete all");
    }
}
