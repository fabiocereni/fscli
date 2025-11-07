package ch.supsi.fscli.frontend.model.i18n;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;

import java.io.IOException;
import java.util.*;

public class SupportedLanguageModel implements ISupportedLanguageModel {

    private final ISupportedLanguageController supportedLanguageController;

    private static SupportedLanguageModel myself;

    private static final String supportedLanguagePropertiesPath = "/supported-languages.properties";
    private static final String translationPropertiesLabel = "labels";

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
        supportedLanguageController.setSupportedLanguagesTags(loadSupportedLanguages(supportedLanguagePropertiesPath));
    }

    @Override
    public HashMap<String, HashMap<String, String>>  setMapLanguages() {
        List<String> languagesTag = getSupportedLanguagesTags();
        HashMap<String, HashMap<String, String>> listMapLanguages = new HashMap<>();

        for (String tag : languagesTag) {
            Locale currentLocale = new Locale(tag);

            ResourceBundle bundle = ResourceBundle.getBundle(translationPropertiesLabel, currentLocale);
            HashMap<String, String> mapLanguage = new HashMap<>();
            for (String key : bundle.keySet()) {
                String value = bundle.getString(key);
                mapLanguage.put(key, value);
            }
            listMapLanguages.put(tag, mapLanguage);
        }
        return listMapLanguages;
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageController.getSupportedLanguagesTags();
    }

    private List<String> loadSupportedLanguages(String path) {
        Properties supportedLanguages = new Properties();
        try {
            supportedLanguages.load(getClass().getResourceAsStream(path));
        } catch (IOException e) {
            System.err.println("WARN: File 'supported-languages.properties' non trovato.");
        }
        return supportedLanguages.values().stream()
                .map(Object::toString)
                .toList();
    }

}
