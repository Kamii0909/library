package hust.kien.project.service.auth;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.AuthorSpecificationBuilder;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;

@SpringBootTest
public class AuthenticationAndAuthorizationServiceTest {

   @Autowired
   private AuthService authService;

   @ParameterizedTest
   @ValueSource(strings = {"kienht3", "kienht4", "kienht5"})
   void testAuth(String authenticationKey) {

       AuthorizedContextHolder securityContext =
           authService.auth(authenticationKey, authenticationKey);

       assertNotNull(securityContext);

       // ht3 = lib, man
       // ht4 = audit, lib
       // ht5 = all
       LibrarianService librarianService = securityContext.getLibrarianService();

       List<Book> books = librarianService.dynamicFind(new BookSpecificationBuilder()
       .nameContains("12")
       .releasedBetween(1999, 2022));

       System.out.println(books);

       assertNotNull(librarianService);

       // list all authors
       List<Author> authors = librarianService.dynamicFind(new AuthorSpecificationBuilder());


       int size = authors.size();

       // 1/6 total of author will be randomly added to a book
       int[] ints = new Random().ints(0, 5).limit(size).toArray();

       Set<Author> authorSet = new HashSet<>();

       for (int i = 0; i < size; i++) {
           if (ints[i] == 0) {
               authorSet.add(authors.get(i));
           }
       }



       Book book = Book
           .builder()
           .name("Book created from Service")
           .releasedYear(2022)
           .stock(7)
           .reimburseCost(40)
           .build();

       book.getBookInfo().setAuthors(authorSet);

       book = librarianService.saveOrUpdate(book);

       book = librarianService
           .dynamicFind(new BookSpecificationBuilder().withId(book.getId()).initCollection()
               .authors().back())
           .get(0);

       System.out.println(authorSet.stream().map(Author::getId).collect(Collectors.toList()));

       assertEquals(
           authorSet.stream().map(Author::getId)
               .collect(Collectors.toCollection(TreeSet::new)).hashCode(),
           book.getBookInfo().getAuthors().stream().map(Author::getId)
               .collect(Collectors.toCollection(TreeSet::new)).hashCode());
   }

}
