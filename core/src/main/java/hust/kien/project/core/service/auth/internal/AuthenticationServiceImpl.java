package hust.kien.project.core.service.auth.internal;

import hust.kien.project.core.auth.EmployeeService;
import hust.kien.project.core.auth.LibraryEmployee;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final EmployeeService metadataService;

    private final MessageDigest encryptor;

    public AuthenticationServiceImpl(EmployeeService metadataService) {
        this.metadataService = metadataService;
        try {
            encryptor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    public LibraryEmployee authenticate(String username, String password)
            throws NoUserFoundException, BadCredentialException {


        LibraryEmployee employee = metadataService.findById(username);
        if (employee == null) {
            throw new NoUserFoundException(username);
        }

        encryptor.update(password.getBytes());
        encryptor.update(employee.getSalt());

        if (Arrays.equals(encryptor.digest(), employee.getEncryptedPassword())) {
            return employee;
        } else {
            throw new BadCredentialException(username);
        }
    }

}
