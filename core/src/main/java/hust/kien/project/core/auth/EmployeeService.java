package hust.kien.project.core.auth;

import org.jspecify.annotations.Nullable;

import java.util.List;

// FIXME
public interface EmployeeService {

    LibraryEmployee create(LibraryEmployee employee);

    LibraryEmployee update(LibraryEmployee employee);

    LibraryEmployee delete(String username);

    @Nullable
    LibraryEmployee findById(String username);

    List<LibraryEmployee> all();
}
