package ch.supsi.fscli.frontend.controller;

import java.io.InputStream;
import java.util.Properties;

public class BuildInfoController {

    private static BuildInfoController mySelf;

    private final Properties properties = new Properties();

    private BuildInfoController() {
        String resourceName = "version.properties";
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourceName)) {
            if (is == null) {
                System.err.println("WARN: File 'version.properties' non trovato.");
                properties.setProperty("app.version", "IDE-DEV"); // Default per l'IDE
                properties.setProperty("app.artifactId", "frontend"); // Default
            } else {
                properties.load(is);
            }
        } catch (Exception e) {
            System.err.println("ERRORE: Impossibile caricare 'version.properties'.");
            e.printStackTrace();
        }
    }

    public static BuildInfoController getInstance() {
        if (mySelf == null) {
            mySelf = new BuildInfoController();
        }
        return mySelf;
    }

    public String getVersion() {
        return properties.getProperty("app.version", "unknown");
    }

}