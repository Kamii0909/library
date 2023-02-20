package hust.kien.project.service.auth;

public class NoUserFoundException extends RuntimeException {
    
    public NoUserFoundException(String username) {
        super("No user found with username: " + username);
    }
}
