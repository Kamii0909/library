package hust.kien.project.core.book;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import hust.kien.project.core.dao.book.BookRepository;
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
        return bookRepository.save(entity);
    }
    
    @Override
    public @NonNull Book update(Book entity) {
        return bookRepository.save(entity);
    }
    
    @Override
    public @NonNull Book delete(Book entity) {
        bookRepository.delete(entity);
        return entity;
    }
    
    @Override
    public List<@NonNull Book> find(BookFilter filter, BookSchema schema) {
        return bookRepository.findAll(filter.build(), schema);
    }
    
}
