package hust.kien.project.core.service.auth;

import java.util.Map;

import hust.kien.project.core.model.auth.LibraryRole;
import hust.kien.project.core.service.authorized.AuditService;
import hust.kien.project.core.service.authorized.AuthorizedService;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.authorized.ManagerService;

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

    public LibrarianService getLibrarianService() {
        return (LibrarianService) authorizedServices.get(LibraryRole.LIBRARIAN);
    }

    public ManagerService getManagerService() {
        return (ManagerService) authorizedServices.get(LibraryRole.MANAGER);
    }

    public AuditService getAuditService() {
        return (AuditService) authorizedServices.get(LibraryRole.AUDIT);
    }
}
