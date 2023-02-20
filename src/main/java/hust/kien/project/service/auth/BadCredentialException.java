package hust.kien.project.service.auth;

public class BadCredentialException extends RuntimeException {

    public BadCredentialException(String username) {
        super("Wrong password for username: " + username);
    }

}
