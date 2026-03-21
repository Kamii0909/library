package hust.kien.project.core.service.auth.internal;

import hust.kien.project.core.auth.LibraryEmployee;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;

public interface AuthorizationService {

    AuthorizedContextHolder authorize(LibraryEmployee user);

}
