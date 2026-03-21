package hust.kien.project.core.service.auth;

import hust.kien.project.core.auth.LibraryRole;
import hust.kien.project.core.service.authorized.AuthorizedService;

import java.util.Map;

public class AuthorizedContextHolder {

    private String username;

    private Map<LibraryRole, AuthorizedService> authorizedServices;

    public AuthorizedContextHolder(String username,
        Map<LibraryRole, AuthorizedService> authorizedServices) {
        this.username = username;
        this.authorizedServices = authorizedServices;
    }

    public String getUsername() {
        return username;
    }

    public boolean isLibrarian() {
        return authorizedServices.containsKey(LibraryRole.LIBRARIAN);
    }

    public boolean isManager() {
        return authorizedServices.containsKey(LibraryRole.MANAGER);
    }


    public boolean isAuditor() {
        return authorizedServices.containsKey(LibraryRole.AUDIT);
    }
}
