package hust.kien.project.core.book;

import org.jspecify.annotations.NonNull;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.service.CrudService;

public interface BookService extends CrudService<@NonNull Book, @NonNull BookFilter, @NonNull BookSchema> {
    
}
