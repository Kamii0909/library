package hust.kien.project.service.internal;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Pageable;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;

public interface LibraryMetadataService {

    <T> T saveOrUpdate(T entity);

    <T, ID extends Serializable> T getReference(Class<T> clazz, ID id);

    <T> void delete(T entity);

    <T> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> spec);

    <T> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> spec, Pageable pageable);

    List<BookGenre> findGenreByNameContains(String name);

}
