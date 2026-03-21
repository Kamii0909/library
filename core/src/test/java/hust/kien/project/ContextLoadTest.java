package hust.kien.project;

import hust.kien.project.core.CoreService;
import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.ReadonlyBook;
import hust.kien.project.core.book.internal.BookFilter;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

import java.util.List;

@SpringBootTest
@ContextConfiguration(classes = CoreService.class)
class ContextLoadTest {
    @Autowired
    private BookService bookService;

    @Test
    void contextLoad() {
        BookFilter filter = new BookFilter()
                .withAtLeastOneGenreLike("aa");

        List<ReadonlyBook> books = bookService.find(filter);
        System.out.println(books);
    }
}
