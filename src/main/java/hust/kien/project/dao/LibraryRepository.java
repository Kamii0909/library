package hust.kien.project.dao;

import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface LibraryRepository<T,ID extends Serializable> extends JpaRepository<T,ID>, JpaSpecificationExecutor<T> {
    
    @Override
    default void deleteAll() {
        throw new UnsupportedOperationException("Don't delete all");
    }
}
