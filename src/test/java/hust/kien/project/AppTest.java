package hust.kien.project;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import hust.kien.project.service.dynamic.ActiveTicketSpecificationBuilder;
import hust.kien.project.service.dynamic.TicketSpecificationBuilder;
import hust.kien.project.service.internal.LibraryMetadataService;
import hust.kien.project.service.internal.TicketService;

@SpringBootTest
public class AppTest {

    @Autowired
    TicketService ticketService;

    @Autowired
    LibraryMetadataService metadataService;

    @Test
    void contextLoad() {
        System.out.println(metadataService.dynamicFind(new TicketSpecificationBuilder()));

        System.out.println("\n\n");
        metadataService
            .dynamicFind(new ActiveTicketSpecificationBuilder())
            .stream()
            .forEach(at -> System.out.println(at));
    }
}
