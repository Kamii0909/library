package hust.kien.project.service;

import java.util.List;
import hust.kien.project.model.auth.LibraryUser;
import hust.kien.project.service.auth.AuthorizedService;

public interface AuthorizationService {
    
    List<AuthorizedService> authorize(LibraryUser user);
}
