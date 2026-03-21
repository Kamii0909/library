package hust.kien.project.core.author;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.Utils;
import hust.kien.project.core.book.BookId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static hust.kien.project.core.Utils.covarianceCast;

@Service
class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author create(AuthorCreationRequest request) {
        Author author = new Author(request.name(), request.dateOfBirth());
        return repository.persist(author);
    }

    @Override
    public Author delete(AuthorId authorId) {
        Long id = authorId.id();
        Author author = repository.find(id);

        if (author == null) {
            throw authorNotFound(authorId);
        }

        repository.deleteById(id);
        return author;
    }

    private IllegalArgumentException authorNotFound(AuthorId id) {
        return new IllegalArgumentException("Author " + id + " not found");
    }

    @Override
    public List<ReadonlyAuthor> find(AuthorFilter filter) {
        return covarianceCast(repository.find(filter.build()));
    }

    @Override
    @Transactional
    public Author update(AuthorUpdateRequest request) {
        Long id = request.id();
        Author author = repository.find(id);

        if (author == null) {
            throw new IllegalArgumentException("Author not found");
        }

        String name = request.name();
        if (name != null)
            author.setName(name);

        NullableUpdate<LocalDate> dob = request.dob();
        if (dob != null)
            author.setDateOfBirth(dob.value());

        return repository.merge(author);
    }


    @Override
    public List<ReadonlyAuthor> authorsOf(BookId bookId) {
        return covarianceCast(repository.authorsOf(bookId));
    }


}
