package hust.kien.project.service.auth;

public interface AuthService {
    /**
     * Authenticate and authorize
     */
    AuthorizedContextHolder auth(String username, String password);
}
