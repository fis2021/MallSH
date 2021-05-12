package userr.services;

import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import userr.exceptions.*;
import userr.model.User;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {


    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER=".test-registration";
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }
    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
    }
    @Test
    @DisplayName("Database is initialize, there are no users")
    void DatabaseIsInitializeAndNoUsersArePersisted() {
        assertThat(UserService.getAllUsers()).isNotNull();
        assertThat(UserService.getAllUsers()).isEmpty();
    }

    @Test
    @DisplayName("User is successfully persisted to Database ")
    void testUserIsAddedToDatabase() throws FieldNotCompletedException, PasswordConfirmationException, UsernameAlreadyExistsException, WeakPasswordException {
        UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
        assertThat(UserService.getAllUsers()).isNotEmpty();
        assertThat(UserService.getAllUsers()).size().isEqualTo(1);
        User user = UserService.getAllUsers().get(0);
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("kris");
        assertThat(user.getPassword()).isEqualTo(UserService.encodePassword("kris","Kristine17!"));
        assertThat(user.getPasswordconfirm()).isEqualTo(UserService.encodePassword("kris","Kristine17!"));
        assertThat(user.getAddress()).isEqualTo("Timisoara");
        assertThat(user.getPhonenumber()).isEqualTo("0744670830");
        assertThat(user.getPhotoPath()).isEqualTo("aa");
        assertThat(user.getFirstname()).isEqualTo("Kristine");
        assertThat(user.getSecondname()).isEqualTo("Senciuc");

    }

    @Test
    @DisplayName("User cannot be added twice")
    void testUserCannotBeAddedTwice()  {
       assertThrows(UsernameAlreadyExistsException.class,()->{
           UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
           UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
       });
    }
    @Test
    @DisplayName("Username does not exists")
    public void testCheckUserDoesAlreadyExist() throws Exception {
    assertThrows(UsernameDoesNotExistsException.class,()->{
    UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
    UserService.checkUserDoesAlreadyExist("kris1");
     });
    }

    @Test
    @DisplayName("Username already exists")
    public void testCheckUserDoesNotExists() {
        assertThrows(UsernameAlreadyExistsException.class,()->{
            UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
            UserService.checkUserDoesNotAlreadyExist("kris");
        });
    }

    @Test
    @DisplayName("Check password")
    void testCheckPassword() {
        assertThrows(WrongPasswordException.class,()->{
            UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
            UserService.checkPassword("Kristine177!","kris");
        });
    }

    @Test
    @DisplayName("Check password match")
    void testPasswordMatch() {
        assertThrows(PasswordConfirmationException.class,()->{
            UserService.checkPasswordsMach("Kristine1!","Kristine17!");
        });
    }

    @Test
    @DisplayName("Check all fields complete for registration")
    void testFieldsComplete() {
        assertThrows(FieldNotCompletedException.class,()->{
            UserService.checkAllFieldCompleted("","","","","","");
        });
    }

    @Test
    @DisplayName("Check all fields complete for log in")
    void testFieldsComplete1() {
        assertThrows(FieldNotCompletedException.class,()->{
            UserService.checkAllFieldCompleted("","");
        });
    }

    @Test
    @DisplayName("Check password format")
    void testPasswordFormat() {
        assertThrows(WeakPasswordException.class,()->{
            UserService.checkPasswordformatException("parola");
        });
    }
    @Test
    @DisplayName("Password encoding")
    public void testPasswordEncoding() {
        assertNotEquals("testPass1", UserService.encodePassword("username", "testPass1"));
    }
    @Test
    @DisplayName("Log in password confirmation")
    public void testPasswordConfirmation() throws Exception {
        UserService.addUser("kris","Kristine17!","Kristine17!","Kristine","Senciuc","0744670830","Timisoara","aa");
        assertNotEquals(UserService.encodePassword("kris", "Kristine17!"),UserService.encodePassword("kris", "Kristine177!"));
    }
    @Test
    @DisplayName("Password contains number")
    public void testStringContainsNumber() throws Exception {
        assertEquals(true,UserService.stringContainsNumber("kristine17"));
    }

    @Test
    @DisplayName("Password contains Upper Case")
    public void testStringContainsUpperCase() throws Exception {
        assertEquals(true,UserService.stringContainsUpperCase("Kristine17"));
    }

}