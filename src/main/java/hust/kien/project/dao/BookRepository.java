package hust.kien.project.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import hust.kien.project.model.book.Book;

@Repository
public interface BookRepository extends LibraryRepository<Book, Long>{

    public List<Book> findByBookInfo_Name(String name);

    public List<Book> findByBookInfo_NameIgnoreCaseLike(String name);

    public List<Book> findByBookInfo_ReleasedYearBetween(int from, int to);

    public List<Book> findByBookStock_ReimburseCostBetween(double from, double to);

    public List<Book> findByBookStock_StockBetween(int from, int to);
}
