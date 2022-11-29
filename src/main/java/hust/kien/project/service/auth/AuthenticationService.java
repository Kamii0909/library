package hust.kien.project.service.auth;

import hust.kien.project.model.auth.LibraryEmployee;

public interface AuthenticationService {
    
    LibraryEmployee authenticate(String username, String password);
}
