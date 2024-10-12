package hust.kien.project.core.service.auth.internal;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.kien.project.core.model.auth.LibraryEmployee;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;
import hust.kien.project.core.service.dynamic.LibraryEmployeeSpecificationBuilder;
import hust.kien.project.core.service.internal.LibraryMetadataService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LibraryMetadataService metadataService;

    private MessageDigest encryptor;

    @Override
    public LibraryEmployee authenticate(String username, String password)
        throws NoUserFoundException, BadCredentialException {
        try {
            encryptor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LibraryEmployee employee = metadataService
            .dynamicFind(new LibraryEmployeeSpecificationBuilder().withUsername(username))
            .stream().findAny().orElseThrow(() -> new NoUserFoundException(username));



        encryptor.update(password.getBytes());
        encryptor.update(employee.getSalt());

        if (Arrays.equals(encryptor.digest(), employee.getEncryptedPassword())) {
            return employee;
        } else {
            throw new BadCredentialException(username);
        }
    }

}
