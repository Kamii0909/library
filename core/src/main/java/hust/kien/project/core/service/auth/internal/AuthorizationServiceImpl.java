package hust.kien.project.core.service.auth.internal;

import java.util.EnumMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.kien.project.core.model.auth.LibraryEmployee;
import hust.kien.project.core.model.auth.LibraryRole;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.core.service.authorized.AuditService;
import hust.kien.project.core.service.authorized.AuthorizedService;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.authorized.ManagerService;

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

        synchronized (this) {
            currentlyLoggedInEmployee = employee;
        }

        Map<LibraryRole, AuthorizedService> map = new EnumMap<>(LibraryRole.class);

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

    public static LibraryEmployee getCurrentlyLoggedInEmployee() {
        return currentlyLoggedInEmployee;
    }

}
