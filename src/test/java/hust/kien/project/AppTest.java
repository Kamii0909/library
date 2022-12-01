package hust.kien.project;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import hust.kien.project.service.auth.AuthService;
import hust.kien.project.service.auth.AuthorizedContextHolder;
import hust.kien.project.service.authorized.ManagerService;

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
    }
}
