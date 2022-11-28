package hust.kien.project.service.auth;

import java.time.LocalDate;
import java.util.List;
import hust.kien.project.model.auth.LibraryEmployee;

public interface ManagerService extends AuthorizedService {
    
    LibraryEmployee createUser(LibraryEmployee user);

    List<LibraryEmployee> findEmployeeFromName(String employeeName);

    void deleteUser(LibraryEmployee employee);

    long income(LocalDate from, LocalDate to);
}
