package hust.kien.project.service.auth;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.service.authorized.AuditService;
import hust.kien.project.service.authorized.AuthorizedService;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.authorized.ManagerService;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static LibraryEmployee currentlyLoggedInEmployee;

    @Autowired
    private AuditService auditService;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private ManagerService managerService;

    @Override
    public AuthorizedContextHolder authorize(LibraryEmployee employee) {
        currentlyLoggedInEmployee = employee;

        Map<LibraryRole, AuthorizedService> map = new HashMap<>();

        for (LibraryRole role : employee.getRoles()) {
            map.put(role, getService(role));
        }

        return new AuthorizedContextHolder(employee.getUsername(), map);
    }

    private AuthorizedService getService(LibraryRole role) {
        switch (role) {
            case MANAGER:
                return managerService;
            case AUDIT:
                return auditService;
            case LIBRARIAN:
                return librarianService;
        }
        throw new IllegalArgumentException(
            "Authorization Service Exception: role not manager, audit or librarian");
    }

    @Override
    public LibraryEmployee getCurrentlyLoggedInEmployee() {
        return currentlyLoggedInEmployee;
    }

}
