package hust.kien.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.auth.AuthService;
import hust.kien.project.service.auth.AuthorizedContextHolder;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.authorized.ManagerService;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;

@SpringBootTest
public class AppTest {

   @Autowired
   ManagerService managerService;


   @Autowired
   AuthService authorizationService;

   @Test
   void contextLoad() {
       AuthorizedContextHolder securityContext = authorizationService.auth("kienht3", "kienht3");

       assertNotNull(securityContext);

       LibrarianService librarianService = securityContext.getLibrarianService();

       Book book = librarianService.dynamicFind(
           new BookSpecificationBuilder()
               .nameContains("test")
               .stockBetween(1, 1000)
               .releasedBetween(2022, 2023)
               .initCollection()
               .authors()
               .genres()
               .back())
           .get(0);

       book.getBookInfo().setName("123 changed");

       librarianService.saveOrUpdate(book);
   }
}

