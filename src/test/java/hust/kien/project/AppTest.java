package hust.kien.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.hibernate5.SpringSessionContext;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.service.AuthenticationService;
import hust.kien.project.service.auth.ManagerService;

@SpringBootTest
public class AppTest {

    @Autowired
    ManagerService managerService;

    @Autowired
    AuthenticationService authenService;

    @Test
    void contextLoad() {
        LibraryEmployee employee = LibraryEmployee.builder()
            .employeeName("Ha Trung Kien 3")
            .username("kienht3")
            .roles(List.of(LibraryRole.LIBRARIAN, LibraryRole.MANAGER))
            .build();

        managerService.createUser(employee, "kienht3");

        LibraryEmployee emp = authenService.authenticate("kienht3", "kienht3");

        assertNotNull(emp);

        long i = managerService.income(LocalDate.ofYearDay(1900, 1), LocalDate.ofYearDay(2050, 1));
        System.out.println(i);

        long i2 = managerService.income(LocalDate.ofYearDay(1900, 1), LocalDate.ofYearDay(2050, 1));
        System.out.println(i2);
    }
}
