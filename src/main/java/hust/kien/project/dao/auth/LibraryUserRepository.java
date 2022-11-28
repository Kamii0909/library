package hust.kien.project.dao.auth;

import org.springframework.stereotype.Repository;
import hust.kien.project.dao.LibraryRepository;
import hust.kien.project.model.auth.LibraryEmployee;

@Repository
public interface LibraryUserRepository extends LibraryRepository<LibraryEmployee, Long> {
    
}
