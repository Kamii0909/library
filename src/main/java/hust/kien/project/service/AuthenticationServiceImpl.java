package hust.kien.project.service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.service.dynamic.LibraryEmployeeSpecificationBuilder;
import hust.kien.project.service.internal.LibraryMetadataService;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    @Autowired
    private LibraryMetadataService metadataService;

    private MessageDigest encryptor;

    @Override
    public LibraryEmployee authenticate(String username, String password) {
        try {
            encryptor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        LibraryEmployee employee =
            metadataService
                .dynamicFind(new LibraryEmployeeSpecificationBuilder().withUsername(username))
                .stream().findAny().orElse(null);

        if (employee == null) {
            throw new RuntimeException("Not correct username");
        }

        encryptor.update(password.getBytes());
        encryptor.update(employee.getSalt());

        return Arrays.equals(encryptor.digest(), employee.getEncryptedPassword()) ? employee : null;
    }

}
