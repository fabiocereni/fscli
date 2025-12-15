package ch.supsi.fscli.frontend.controller;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.InputStream;
import java.util.Properties;

@Singleton
public class BuildInfoController {
    private final Properties properties = new Properties();

    @Inject
    public BuildInfoController() {
        String resourceName = "version.properties";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                System.err.println("WARN: File 'version.properties' non trovato.");
                properties.setProperty("app.version", "IDE-DEV"); // Default per l'IDE
                properties.setProperty("app.artifactId", "frontend"); // Default
                properties.setProperty("app.description", "Torri, Ferraris, Cereni"); // Default
            } else {
                properties.load(is);
            }
        } catch (Exception e) {
            System.err.println("ERRORE: Impossibile caricare 'version.properties'.");
            e.printStackTrace();
        }
    }

    public String getVersion() {
        return properties.getProperty("app.version", "unknown");
    }
    public String getDescription() {
        return properties.getProperty("app.description", "unknown");
    }
    public String getDate() {
        return properties.getProperty("build.date", "unknown");
    }


}