package hust.kien.project.service.auth;

import java.time.LocalDate;
import hust.kien.project.model.auth.LibraryEmployee;

public interface ManagerService extends AuthorizedService {
    
    void createUser(LibraryEmployee user, String password);

    void deleteUser(String username);

    long income(LocalDate from, LocalDate to);
}
