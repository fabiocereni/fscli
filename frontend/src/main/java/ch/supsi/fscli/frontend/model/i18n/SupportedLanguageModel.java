package ch.supsi.fscli.frontend.model.i18n;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;

import java.io.IOException;
import java.util.List;
import java.util.Properties;

public class SupportedLanguageModel implements ISupportedLanguageModel {

    private final ISupportedLanguageController supportedLanguageController;

    private static SupportedLanguageModel myself;

    private static final String supportedLanguagePropertiesPath = "/supported-languages.properties";

    private SupportedLanguageModel() {
        supportedLanguageController = SupportedLanguageController.getInstance();
    }

    public static SupportedLanguageModel getInstance() {
        if (myself == null) {
            myself = new SupportedLanguageModel();
        }
        return myself;
    }

    @Override
    public void setSupportedLanguagesTags() {
        supportedLanguageController.setSupportedLanguagesTags(loadSupportedLanguages());
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageController.getSupportedLanguagesTags();
    }

    private List<String> loadSupportedLanguages() {
        Properties supportedLanguages = new Properties();
        try {
            supportedLanguages.load(getClass().getResourceAsStream(supportedLanguagePropertiesPath));
        } catch (IOException e) {
            System.err.println("WARN: File 'supported-languages.properties' non trovato.");
        }
        return supportedLanguages.values().stream()
                .map(Object::toString)
                .toList();
    }
}
