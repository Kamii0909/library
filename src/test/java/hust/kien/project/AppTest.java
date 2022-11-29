package hust.kien.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.service.auth.AuthenticationService;
import hust.kien.project.service.auth.AuthorizationService;
import hust.kien.project.service.auth.AuthorizedContextHolder;
import hust.kien.project.service.authorized.ManagerService;

@SpringBootTest
public class AppTest {

    @Autowired
    ManagerService managerService;

    @Autowired
    AuthenticationService authenService;

    @Autowired
    AuthorizationService authorizationService;

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

        AuthorizedContextHolder securityContext = authorizationService.authorize(emp);

        assertNotNull(securityContext.getLibrarianService());

        assertNull(securityContext.getAuditService());
    }
}
