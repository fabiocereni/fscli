package ch.supsi.fscli.frontend.model.preference;

import ch.supsi.fscli.backend.application.preference.IPreferencesApplication;
import ch.supsi.fscli.backend.application.preference.PreferencesApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.*;
import java.util.Properties;

@Singleton
public class PreferencesModel implements IPreferencesModel {

    public static final String KEY_LANGUAGE = "language-tag";
    public static final String KEY_FONT_COMMANDLINE = "font-commandLine";
    public static final String KEY_FONT_OUTPUT_AREA = "font-output-area";
    public static final String KEY_FONT_LOG_AREA = "font-log-area";
    public static final String KEY_LINES_NUMBER = "number-lines";

    public static final String DEFAULT_LANGUAGE = "it_IT";
    public static final String DEFAULT_FONT_COMMANDLINE = "Consolas";
    public static final String DEFAULT_FONT_OUTPUT_AREA = "Arial";
    public static final String DEFAULT_FONT_LOG_AREA = "Comic Sans MS";
    public static final int DEFAULT_LINES_NUMBER = 25;

    // da iniettare manualmente
    private final IPreferencesApplication preferencesApplication;

    private String preferencesPath;

    @Inject
    public PreferencesModel(IPreferencesApplication preferencesApplication) {
        this.preferencesApplication = preferencesApplication;
    }

    // da vedere
    @Inject
    private void manageProperties() {
        String userHome = System.getProperty("user.home");
        String folderApp = userHome + File.separator + "user-pref";
        this.preferencesPath = folderApp + File.separator + "user.properties";

        new File(folderApp).mkdirs();

        File propertiesFile = new File(preferencesPath);

        if (propertiesFile.exists()) {
            try (FileInputStream input = new FileInputStream(propertiesFile)) {
                getProperties().load(input);
                System.out.println("Preferenze caricate da: " + preferencesPath);
            } catch (IOException e) {
                System.out.println("Errore durante il caricamento delle preferenze.");
                e.printStackTrace();
            }
        } else {
            System.out.println("File non trovato. Creazione file con valori di default.");

            getProperties().setProperty(KEY_LANGUAGE, DEFAULT_LANGUAGE);
            getProperties().setProperty(KEY_FONT_COMMANDLINE, DEFAULT_FONT_COMMANDLINE);
            getProperties().setProperty(KEY_FONT_OUTPUT_AREA, DEFAULT_FONT_OUTPUT_AREA);
            getProperties().setProperty(KEY_FONT_LOG_AREA, DEFAULT_FONT_LOG_AREA);
            getProperties().setProperty(KEY_LINES_NUMBER, String.valueOf(DEFAULT_LINES_NUMBER));

            savePreferences();
        }
    }

    @Override
    public void savePreferences() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(preferencesPath))) {
            for (String key : getProperties().stringPropertyNames()) {
                String value = getProperties().getProperty(key);
                writer.write(key + "=" + value);
                writer.newLine();
            }
            System.out.println("Preferenze salvate in: " + preferencesPath);
        } catch (IOException e) {
            System.out.println("Errore durante il salvataggio delle preferenze.");
            e.printStackTrace();
        }
    }

    @Override
    public String getProperty(String key) {
        return preferencesApplication.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        preferencesApplication.setProperty(key, value);
    }

    @Override
    public Properties getProperties() {
        return preferencesApplication.getProperties();
    }

}
