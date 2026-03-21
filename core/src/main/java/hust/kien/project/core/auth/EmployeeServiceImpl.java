package hust.kien.project.core.auth;

import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository repository;

    EmployeeServiceImpl(EmployeeRepository repository) {
        this.repository = repository;
    }


    @Override
    public LibraryEmployee create(LibraryEmployee employee) {
        return repository.persist(employee);
    }

    @Override
    public LibraryEmployee update(LibraryEmployee employee) {
        return repository.merge(employee);
    }

    @Override
    public LibraryEmployee delete(String username) {
        LibraryEmployee employee = repository.findById(username).orElseThrow();
        repository.delete(employee);
        return employee;
    }

    @Override
    public @Nullable LibraryEmployee findById(String username) {
        return repository.find(username);
    }

    @Override
    public List<LibraryEmployee> all() {
        return repository.findAll();
    }
}
