package hust.kien.project.service.auth.internal;

import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.service.auth.AuthorizedContextHolder;

public interface AuthorizationService {
    
    AuthorizedContextHolder authorize(LibraryEmployee user);

    LibraryEmployee getCurrentlyLoggedInEmployee();
}
