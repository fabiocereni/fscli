package ch.supsi.fscli.frontend.model;

import ch.supsi.fscli.frontend.controller.PreferencesController;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Properties;

public final class PreferencesModel implements IPreferencesModel {

    private static final String DEFAULT_PREFS_RESOURCE = "/default-user-preferences.properties";
    private static final String APP_DIR = ".fscli_preferences";
    private static final String PREFS_FILE = "preferences.properties";

    private static PreferencesModel instance;

    private Properties properties;
    private Path prefsFilePath;

    private PreferencesModel() {}

    public static synchronized PreferencesModel getInstance() {
        if (instance == null) {
            instance = new PreferencesModel();
            instance.load();
        }
        return instance;
    }

    private void load() {
        Path home = Path.of(System.getProperty("user.home"));
        Path appDir = home.resolve(APP_DIR);
        prefsFilePath = appDir.resolve(PREFS_FILE);

        if (Files.exists(prefsFilePath)) {
            properties = loadFromFile(prefsFilePath);
        } else {
            properties = loadDefaults();
            createUserPrefsFile(appDir);
        }
    }

    private Properties loadDefaults() {
        Properties defaults = new Properties();
        try (InputStream is = getClass().getResourceAsStream(DEFAULT_PREFS_RESOURCE)) {
            if (is != null) defaults.load(is);
        } catch (IOException e) {
            System.err.println("Warning: default-user-preferences.properties non trovato");
        }
        return defaults;
    }

    private Properties loadFromFile(Path path) {
        Properties props = new Properties();
        try (InputStream is = Files.newInputStream(path)) {
            props.load(is);
        } catch (IOException e) {
            System.err.println("Warning: errore lettura preferenze utente, uso default");
            return loadDefaults();
        }
        return props;
    }

    private void createUserPrefsFile(Path appDir) {
        try {
            Files.createDirectories(appDir);
            try (FileOutputStream fos = new FileOutputStream(prefsFilePath.toFile())) {
                properties.store(fos, "FSCLI User Preferences – created automatically");
            }
        } catch (IOException e) {
            System.err.println("Warning: impossibile creare cartella/file preferences");
        }
    }

    // GETTER
    public String get(String key) {
        return properties.getProperty(key);
    }

    public String get(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        try {
            return Integer.parseInt(properties.getProperty(key, String.valueOf(defaultValue)));
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    // SETTER
    public void set(String key, String value) {
        properties.setProperty(key, value);
    }

    public void set(String key, int value) {
        properties.setProperty(key, String.valueOf(value));
    }

    // SALVATAGGIO
    public void save() {
        if (prefsFilePath == null) return;
        try (FileOutputStream fos = new FileOutputStream(prefsFilePath.toFile())) {
            properties.store(fos, "FSCLI User Preferences – updated");
        } catch (IOException e) {
            System.err.println("Warning: impossibile salvare le preferenze");
        }
    }

    // METODI COMODI PER FSCLI
    public String getLanguage() {
        return get("language-tag", "en_US");
    }

    public void setLanguage(String lang) {
        set("language-tag", lang);
    }

    public int getOutputLines() {
        return getInt("output-lines", 25);
    }

    public void setOutputLines(int lines) {
        set("output-lines", lines);
    }
}