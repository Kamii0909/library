package hust.kien.project.service;

import hust.kien.project.model.auth.LibraryEmployee;

public interface AuthenticationService {
    
    LibraryEmployee authenticate(String username, String password);
}
