package hust.kien.project.service;

import java.util.List;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookInfo;

public interface BookService extends LibraryService<Book> {

    //-----------------
    // FIND BY BOOK INFO
    //-----------------
    public List<Book> findByBookInfo(BookInfo bookInfo);

    
}
