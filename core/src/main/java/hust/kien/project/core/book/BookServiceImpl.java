package hust.kien.project.core.book;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hust.kien.project.core.model.book.Book;

@Service
@Transactional
class BookServiceImpl implements BookService {
    
    private final BookRepository bookRepository;
    
    public BookServiceImpl(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }
    
    @Override
    public @NonNull Book create(Book entity) {
        return bookRepository.persist(entity);
    }
    
    @Override
    public @NonNull Book update(Book entity) {
        return bookRepository.merge(entity);
    }
    
    @Override
    public @NonNull Book delete(Book entity) {
        bookRepository.delete(entity);
        return entity;
    }
    
    @Override
    public List<@NonNull Book> find(BookFilter filter, BookSchema schema) {
        List<@NonNull Book> books = bookRepository.find(filter.build());
        
        if (books.isEmpty())
            return books;
        
        if (schema.isAuthors())
            books.get(0).getBookInfo().authors().size();
        if (schema.isGenres())
            books.get(0).getBookInfo().bookGenres().size();
        if (schema.isCompletedContracts())
            books.get(0).getBookStock().completedContracts().size();
        if (schema.isOngoingContracts())
            books.get(0).getBookStock().ongoingContracts().size();
        
        return books;
    }
}
