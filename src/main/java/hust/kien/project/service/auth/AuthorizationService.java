package hust.kien.project.service.auth;

import hust.kien.project.model.auth.LibraryEmployee;

public interface AuthorizationService {
    
    AuthorizedContextHolder authorize(LibraryEmployee user);

    LibraryEmployee getCurrentlyLoggedInEmployee();
}
