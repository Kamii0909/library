package hust.kien.project.core.service.internal;

import java.io.Serializable;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import hust.kien.project.core.dao.LibraryRepository;
import hust.kien.project.core.dao.LibraryRepositoryFactory;
import hust.kien.project.core.dao.book.BookGenreRepository;
import hust.kien.project.core.model.book.BookGenre;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.service.dynamic.GeneralSpecificationBuilder;

@Service
@SuppressWarnings("unchecked")
@Transactional(propagation = Propagation.REQUIRES_NEW)
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
        if (entity instanceof BookGenre) {
            BookGenre bg = getReference(BookGenre.class, ((BookGenre) entity).getName());
            bg.getBooksWithThisGenre()
                .forEach(book -> book.getBookInfo().removeGenre(bg));
        } else if (entity instanceof Client) {
            Client client = getReference(Client.class, ((Client) entity).getId());
            assert client.getRentInfo().getActiveTickets().isEmpty() : new IllegalStateException(
                "Client ticket should all be closed here");
            client.getRentInfo().getClosedTickets().forEach(this::delete);
        }
        repositoryFactory.getRepository((Class<T>) entity.getClass()).delete(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public <T> List<T> dynamicFind(GeneralSpecificationBuilder<T> spec) {
        return repositoryFactory.getRepository(spec.libraryType()).findAll(spec.build());
    }

    @Override
    @Transactional(readOnly = true)
    public List<BookGenre> findGenreByNameContains(String name) {
        return ((BookGenreRepository) repositoryFactory.getRepository(BookGenre.class))
            .findByNameLikeIgnoreCase("%" + name + "%");
    }

    @Override
    public <T> List<T> dynamicFind(GeneralSpecificationBuilder<T> spec, Pageable pageable) {
        return repositoryFactory.getRepository(spec.libraryType()).findAll(spec.build(),
            pageable.getOffset(), pageable.getPageSize(), pageable.getSort());
    }

}
