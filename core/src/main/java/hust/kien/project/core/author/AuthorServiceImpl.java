package hust.kien.project.core.author;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hust.kien.project.core.NullableUpdate;
import hust.kien.project.core.author.AuthorUpdateRequest.BookChanges;
import hust.kien.project.core.author.AuthorUpdateRequest.BookChanges.Diff;
import hust.kien.project.core.author.AuthorUpdateRequest.BookChanges.Replace;
import hust.kien.project.core.book.BookId;

@Service
class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;

    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }

    @Override
    public Author create(Author entity) {
        return repository.persist(entity);
    }

    @Override
    public Author update(Author entity) {
        return repository.merge(entity);
    }

    @Override
    public Author delete(Author entity) {
        repository.delete(entity);
        return entity;
    }

    @Override
    public List<@NonNull Author> find(AuthorFilter filter) {
        return repository.find(filter.build());
    }

    @Override
    public Stream<@NonNull Author> findStream(AuthorFilter filter) {
        // FIXME
        return find(filter).stream();
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

        BookChanges changes = request.bookChanges();
        if (changes != null) {
            switch (changes) {
                case Diff diff -> {
                    for (BookId bookId : diff.adds()) {
                        author.addBook(bookId);
                    }

                    for (BookId bookId : diff.removes()) {
                        author.removeBook(bookId);
                    }
                }
                case Replace replace -> author.replaceBooks(replace.all());
            }
        }

        return repository.merge(author);
    }

}
