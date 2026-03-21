package hust.kien.project.core.auth;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
class EmployeeRepository extends SimpleCrudRepository<LibraryEmployee, String> {
    public EmployeeRepository(EntityManager entityManager) {
        super(LibraryEmployee.class, entityManager);
    }


}
