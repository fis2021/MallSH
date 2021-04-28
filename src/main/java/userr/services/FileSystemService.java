package userr.services;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileSystemService {
    private static final String APPLICATION_FOLDER = ".registration-example";
    private static final String AD_FOLDER = ".ad_database";
    private static final String USER_FOLDER = System.getProperty("user.home");
    public static final Path APPLICATION_HOME_PATH = Paths.get(USER_FOLDER, APPLICATION_FOLDER);
    public static final Path AD_HOME_PATH = Paths.get(USER_FOLDER, AD_FOLDER);

    public static Path getPathToFile(String... path) {
        return APPLICATION_HOME_PATH.resolve(Paths.get(".", path));
    }
    public static Path getPathToAd(String... path) { return AD_HOME_PATH.resolve(Paths.get(".", path)); }
}
