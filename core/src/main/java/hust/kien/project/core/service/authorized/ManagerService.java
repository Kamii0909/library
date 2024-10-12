package hust.kien.project.core.service.authorized;

import java.time.LocalDate;
import java.util.List;

import hust.kien.project.core.model.auth.LibraryEmployee;

public interface ManagerService extends AuthorizedService {

    boolean isUsernameExist(String username);

    List<LibraryEmployee> getAllEmployees();
    
    void createUser(LibraryEmployee user, String password);

    void deleteUser(String username);

    long income(LocalDate from, LocalDate to);
}
