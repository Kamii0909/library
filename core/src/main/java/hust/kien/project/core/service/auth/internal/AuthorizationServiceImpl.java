package hust.kien.project.core.service.auth.internal;

import hust.kien.project.core.auth.LibraryEmployee;
import hust.kien.project.core.auth.LibraryRole;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.core.service.authorized.AuthorizedService;
import hust.kien.project.core.service.authorized.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.Map;

@Service
public class AuthorizationServiceImpl implements AuthorizationService {

    private static LibraryEmployee currentlyLoggedInEmployee;

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
        return switch (role) {
            case MANAGER -> managerService;
            case AUDIT -> null;
            case LIBRARIAN -> null;
        };
    }

    public static LibraryEmployee getCurrentlyLoggedInEmployee() {
        return currentlyLoggedInEmployee;
    }

}
