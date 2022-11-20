package hust.kien.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class LibraryApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(LibraryApplication.class, args);
        ctx.close();
    }

    @EventListener(classes = ApplicationReadyEvent.class)
    public void runCli() {
        System.out.println("Started application");
    }
}
