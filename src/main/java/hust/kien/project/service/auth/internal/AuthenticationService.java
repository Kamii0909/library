package hust.kien.project.service.auth.internal;

import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.service.auth.BadCredentialException;
import hust.kien.project.service.auth.NoUserFoundException;

public interface AuthenticationService {
    
    LibraryEmployee authenticate(String username, String password) throws NoUserFoundException, BadCredentialException;
}
