package hust.kien.project.service;

import hust.kien.project.model.auth.LibraryUser;

public interface AuthenticationService {
    
    LibraryUser authenticate(String username, String password);
}
