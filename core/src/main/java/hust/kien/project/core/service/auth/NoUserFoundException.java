package hust.kien.project.core.service.auth;

public class NoUserFoundException extends RuntimeException {
    
    public NoUserFoundException(String username) {
        super("No user found with username: " + username);
    }
}
