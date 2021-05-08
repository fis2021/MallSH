package userr.services;


import userr.exceptions.FieldNotCompletedException;
import userr.exceptions.PasswordConfirmationException;
import userr.exceptions.UsernameAlreadyExistsException;
import userr.exceptions.UsernameDoesNotExistsException;
import userr.exceptions.WeakPasswordException;
import userr.exceptions.WrongPasswordException;
import userr.model.User;
import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;


import static userr.services.FileSystemService.getPathToFile;
public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("users_database.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }

    public static void addUser(String username, String password, String passwordconfirm, String firstname,
                               String secondname, String phonenumber, String address,String photoPath)  throws UsernameAlreadyExistsException,FieldNotCompletedException, WeakPasswordException, PasswordConfirmationException {
        checkUserDoesNotAlreadyExist(username);
        checkAllFieldCompleted(username, password, firstname, passwordconfirm, secondname,phonenumber);
        checkPasswordformatException(password);
        checkPasswordsMach(password, passwordconfirm);
        userRepository.insert(new User(username, encodePassword(username, password),encodePassword(username, passwordconfirm), firstname, secondname, phonenumber, address, photoPath));
    }
    public static void loginUser(String username, String password) throws UsernameDoesNotExistsException, WrongPasswordException {
        checkUserDoesAlreadyExist(username);
        checkPassword(password,username);
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
    public static void checkPasswordformatException(String password) throws WeakPasswordException {
        if (password.length()<8)
            throw new WeakPasswordException("8 characters");
        if (!stringContainsNumber(password))
            throw new WeakPasswordException("one digit");
        if (!stringContainsUpperCase(password))
            throw new WeakPasswordException("one upper case");
    }
    public static boolean stringContainsNumber(String s)
    {
        return Pattern.compile( "[0-9]" ).matcher( s ).find();
    }
    public static boolean stringContainsUpperCase(String s)
    {
        return Pattern.compile( "[A-Z]" ).matcher( s ).find();
    }

    public static void checkPasswordsMach(String password, String passwordconfirm) throws PasswordConfirmationException {
        if (!password.trim().equals(passwordconfirm.trim())) {
            throw new PasswordConfirmationException();
        }
    }
    public static void checkUserDoesAlreadyExist(String username) throws UsernameDoesNotExistsException {
        int ok=0;
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                ok=1;
        }
        if(ok==0){
            throw new UsernameDoesNotExistsException(username);
        }
    }

    public static void checkPassword(String password, String username) throws WrongPasswordException {
        int ok=0;
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                if (Objects.equals(encodePassword(username,password), user.getPassword())) {
                    ok = 1;
                }
            }
        }
        if(ok==0) {
            throw new WrongPasswordException();
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
    public static ObjectRepository<User>  getUsers() {
        return userRepository;
    }

}
