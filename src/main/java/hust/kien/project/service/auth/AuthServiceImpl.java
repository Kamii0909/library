package hust.kien.project.service.auth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.service.auth.internal.AuthenticationService;
import hust.kien.project.service.auth.internal.AuthorizationService;

@Service
class AuthServiceImpl implements AuthService {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private AuthorizationService authorizationService;

    @Override
    public AuthorizedContextHolder auth(String username, String password) {
        return authorizationService
            .authorize(authenticationService.authenticate(username, password));
    }

}
