package hust.kien.project.core.service.auth;

public interface AuthService {
    /**
     * Authenticate and authorize
     */
    AuthorizedContextHolder auth(String username, String password)
        throws NoUserFoundException, BadCredentialException;
}
