package hust.kien.project.service.auth;

import java.time.LocalDate;
import java.util.List;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.model.auth.LibraryUser;

public interface ManagerService extends AuthorizedService {
    
    LibraryUser createUser(String username, String password, String employeeName,  List<LibraryRole> roles);

    void deleteUser(LibraryUser user);

    long income(LocalDate from, LocalDate to);
}
