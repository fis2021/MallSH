<img src="resources/logo.png" />

Our application wants to help people and proposes a simple and intuitive interface for people who are at the beginning of digitization and need something simpler than complicated shops with dozens of hidden interfaces.

---

You can buy and sell at a single distance call, using our application.

---
## Technologies:
* [Java 15](https://www.oracle.com/java/technologies/javase-downloads.html)    (backend)
* [JavaFX](https://openjfx.io/openjfx-docs/)    (frontend)
* [Maven](https://maven.apache.org/)
* [Nitrite Java](https://www.dizitart.org/nitrite-database.html) (as Database)

---
##  Jira site:
https://rmekrt.atlassian.net/jira/software/projects/MSH/boards/1

---
## Clone the repository

    git clone https://github.com/fis2021/MallSH.git

---

## Start with maven

Start the application using the command:

`mvn javafx:run`  (run the `run` goal of the `javafx` maven plugin)

You should see an application starting, that looks like this:

<img src="resources/login.png" />


You can login if you already have an account, or you can create one by pressing the button "Register".

After login, you are redirected to the HomePage, and from now everything is so intuitive. Have fun browsing!

## Technical Details



### Encrypting Passwords
Encrypting the passwords is done via the following 2 Java functions, found in [UserService.java](https://github.com/fis2021/MallSH/blob/main/src/main/java/userr/services/UserService.java):
```
    static String encodePassword(String salt, String password) {
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
```

### Nitrite Java
Nitrite Java was used:
* in the [UserService.java](https://github.com/fis2021/MallSH/blob/main/src/main/java/userr/services/UserService.java) file, where we initialized a database, and a _Repository_ of User objects:
```
    private static ObjectRepository<User> userRepository;
    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
        database = Nitrite.builder()
                .filePath(getPathToFile("users_database.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }
```
* in the [AdService.java](https://github.com/fis2021/MallSH/blob/main/src/main/java/userr/services/AdService.java) file, where we initialized a database, and a _Repository_ of Ad objects:
```
 private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static Nitrite database;
    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("ad_database.db").toFile())
                .openOrCreate("test", "test");
        adRepository = database.getRepository(Ad.class);
    }
```
* in the [FavoriteAdService.java](https://github.com/fis2021/MallSH/blob/main/src/main/java/userr/services/FavoriteAdService.java) file, where we initialized a database, and a _Repository_ of Ad (saved as Favorite) objects:
```
 private static ObjectRepository<Ad> adRepository = AdService.getAdRepository();
    private static Nitrite database;
    public static void initDatabase() {
        database = Nitrite.builder()
                .filePath(getPathToFile("ad_database.db").toFile())
                .openOrCreate("test", "test");
        adRepository = database.getRepository(Ad.class);
    }
```
* in the [FeedbackService.java](https://github.com/fis2021/MallSH/blob/main/src/main/java/userr/services/FeedbackService.java) file, where we initialized a database, and a _Repository_ of Feedback objects:
```
 private static ObjectRepository<Feedback> feedbackRepository = FeedbackService.getFeedbackRepository();
    private static Nitrite database;

    public static void initDatabase() {
        FileSystemService.initDirectory();
         database = Nitrite.builder()
                .filePath(getPathToFile("feedback_database.db").toFile())
                .openOrCreate("test", "test");
        feedbackRepository = database.getRepository(Feedback.class);
    }
```

## Resources
To understand and learn more about **JavaFX**, you can take a look at some of the following links:
* [Introduction to FXML](https://openjfx.io/javadoc/16/javafx.fxml/javafx/fxml/doc-files/introduction_to_fxml.html)
* [Getting Started with JavaFX](https://openjfx.io/openjfx-docs/)
* [JavaFX Tutorial](https://code.makery.ch/library/javafx-tutorial/)
* [JavaFX Java GUI Design Tutorials](https://www.youtube.com/playlist?list=PL6gx4Cwl9DGBzfXLWLSYVy8EbTdpGbUIG)

To better understand how to use **Nitrite Java**, use the following links:
* [Nitrite Java Github Repository](https://github.com/nitrite/nitrite-java)
* [Nitrite Java Project Page](https://www.dizitart.org/nitrite-database.html)
* [Nitrite Java Documentation Page](https://www.dizitart.org/nitrite-database/)
* [Nitrite Java: Filters](https://www.dizitart.org/nitrite-database/#filter)
* [Nitrite: How to Create an Embedded Database for Java and Android](https://dzone.com/articles/nitrite-how-to-create-an-embedded-database-for-jav)
* [Nitrite: An Embedded NoSQL Database for Java and Android](https://medium.com/@anidotnet/nitrite-an-embedded-nosql-database-for-java-and-android-318bf48c7758)

