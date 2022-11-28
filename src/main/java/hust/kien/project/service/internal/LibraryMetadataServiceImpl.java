package hust.kien.project.service.internal;

import java.io.Serializable;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import hust.kien.project.dao.AuthorRepository;
import hust.kien.project.dao.BookGenreRepository;
import hust.kien.project.dao.BookRepository;
import hust.kien.project.dao.LibraryRepository;
import hust.kien.project.dao.LibraryRepositoryFactory;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.service.dynamic.GeneralLibrarySpecificationBuilder;

@Service
@Transactional(propagation = Propagation.REQUIRES_NEW)
@SuppressWarnings("unchecked")
public class LibraryMetadataServiceImpl implements LibraryMetadataService {

    @Autowired
    private LibraryRepositoryFactory repositoryFactory;

    @Override
    public <T> T saveOrUpdate(T entity) {
        return repositoryFactory.getRepository((Class<T>) entity.getClass()).save(entity);
    }

    @Override
    public <T, ID extends Serializable> T getReference(Class<T> clazz, ID id) {
        return ((LibraryRepository<T, ID>) repositoryFactory.getRepository(clazz))
            .getReferenceById(id);
    }

    @Override
    public <T> void delete(T entity) {
        repositoryFactory.getRepository((Class<T>) entity.getClass()).delete(entity);
    }

    @Override
    public <T> List<T> dynamicFind(GeneralLibrarySpecificationBuilder<T> spec) {
        return repositoryFactory.getRepository(spec.libraryType()).findAll(spec.build());
    }

    @Override
    public List<Book> findBookByNameContains(String name) {
        return ((BookRepository) repositoryFactory.getRepository(Book.class))
            .findByBookInfo_NameIgnoreCaseLike("%" + name + "%");
    }

    @Override
    public List<Author> findAuthorByNameContains(String name) {
        return ((AuthorRepository) repositoryFactory.getRepository(Author.class))
            .findByAuthorInfo_NameIgnoreCaseLike("%" + name + "%");
    }

    @Override
    public List<BookGenre> findGenreByNameContains(String name) {
        return ((BookGenreRepository) repositoryFactory.getRepository(BookGenre.class))
        .findByNameLikeIgnoreCase("%" + name + "%");
    }

}
