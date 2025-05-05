package hust.kien.project.core.author;

import java.util.List;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;

import hust.kien.project.core.dao.author.AuthorRepository;

@Service
class AuthorServiceImpl implements AuthorService {
    private final AuthorRepository repository;
    
    public AuthorServiceImpl(AuthorRepository repository) {
        this.repository = repository;
    }
    
    @Override
    public Author create(Author entity) {
        return repository.save(entity);
    }
    
    @Override
    public Author update(Author entity) {
        return repository.save(entity);
    }
    
    @Override
    public Author delete(Author entity) {
        repository.delete(entity);
        return entity;
    }
    
    @Override
    public List<@NonNull Author> find(AuthorFilter filter) {
        return repository.findAll(filter.build());
    }
    
    @Override
    public Stream<@NonNull Author> findStream(AuthorFilter filter) {
        // FIXME
        return find(filter).stream();
    }
    
}
