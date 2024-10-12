package hust.kien.project.core.service.auth;

public class BadCredentialException extends RuntimeException {

    public BadCredentialException(String username) {
        super("Wrong password for username: " + username);
    }

}
