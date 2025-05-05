package hust.kien.project.core.dao.book;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.data.jpa.domain.Specification;

import hust.kien.project.core.book.BookSchema;
import hust.kien.project.core.model.book.Book;
import jakarta.persistence.EntityManager;

public class SchemaAwareBookRepositoryImpl implements SchemaAwareBookRepository {
    
    // FIXME: cringe circular dependency
    private final ObjectProvider<BookRepository> repository;
    
    public SchemaAwareBookRepositoryImpl(ObjectProvider<BookRepository> repository, EntityManager entityManager) {
        this.repository = repository;
    }
    
    @Override
    public List<Book> findAll(Specification<Book> specification, BookSchema schema) {
        List<@NonNull Book> books = repository.getIfAvailable().findAll(specification);
        
        if (books.isEmpty())
            return books;
        
        if (schema.isAuthors()) {
            books.get(0).getBookInfo().authors().size();
        }
        if (schema.isGenres()) {
            books.get(0).getBookInfo().bookGenres().size();
        }
        if (schema.isCompletedContracts()) {
            books.get(0).getBookStock().completedContracts().size();
        }
        if (schema.isOngoingContracts()) {
            books.get(0).getBookStock().ongoingContracts().size();
        }
        
        return books;
    }
    
}
