package ch.supsi.fscli.backend.DAO;

import com.google.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Singleton
public class FSDataReaderDAO implements IFSDataReaderDAO {

    private static final String MAIN_DIR = "filesystem_simulator";
    private static final String SUB_DIR = "user_preferences";
    private static final String PREFERENCES_FILE = "preferences.properties";

    @Override
    public String readFromAFile(File file) throws IOException {
        return String.valueOf(Files.readAllLines(file.toPath()));
    }

    @Override
    public Properties getPreferences() {
        Properties properties = new Properties();
        Path preferencesPath = getPreferencesPath();

        if (Files.exists(preferencesPath)) {
            try (InputStream in = Files.newInputStream(preferencesPath)) {
                properties.load(in);
            } catch (IOException e) {
                System.err.println("Error reading preferences: " + e.getMessage());
                e.printStackTrace();
            }
        }

        return properties;
    }

    private Path getPreferencesPath() {
        String userHome = System.getProperty("user.home");
        return Paths.get(userHome, MAIN_DIR, SUB_DIR, PREFERENCES_FILE);
    }
}
