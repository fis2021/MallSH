package userr.services;



import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.UsernameAlreadyExistsException;
import userr.model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import userr.services.FileSystemService;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;
import java.util.regex.Pattern;


import static userr.services.FileSystemService.getPathToFile;
public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("registration-example.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String passwordconfirm, String firstname,
                               String secondname, String phonenumber, String address)  throws UsernameAlreadyExistsException,FieldNotCompletedException {
        checkUserDoesNotAlreadyExist(username);
        checkAllFieldCompleted(username, password, firstname, passwordconfirm, secondname,phonenumber);
        userRepository.insert(new User(username, encodePassword(username, password),encodePassword(username, passwordconfirm), firstname, secondname, phonenumber, address));
    }
    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }
    public static void checkAllFieldCompleted(String username, String password, String firstname, String passwordconfirm,
                                              String secondname, String phonenumber) throws FieldNotCompletedException {
        if (username.trim().isEmpty() || password.trim().isEmpty()|| firstname.trim().isEmpty()||
                passwordconfirm.trim().isEmpty()|| phonenumber.trim().isEmpty()|| secondname.trim().isEmpty()) {
            throw new FieldNotCompletedException();
        }
    }


    private static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }


}
