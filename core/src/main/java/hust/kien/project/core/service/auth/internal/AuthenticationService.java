package hust.kien.project.core.service.auth.internal;

import hust.kien.project.core.auth.LibraryEmployee;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;

public interface AuthenticationService {
    
    LibraryEmployee authenticate(String username, String password) throws NoUserFoundException, BadCredentialException;
}
