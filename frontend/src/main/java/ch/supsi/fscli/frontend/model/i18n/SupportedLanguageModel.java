package ch.supsi.fscli.frontend.model.i18n;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;

import java.io.IOException;
import java.util.*;

public class SupportedLanguageModel implements ISupportedLanguageModel {

    private final ISupportedLanguageController supportedLanguageController;

    private static SupportedLanguageModel myself;

    private static final String supportedLanguagePropertiesPath = "/supported-languages.properties";
    private static final String translationPropertiesLabel = "i18n.labels";

    private String languageTagSelected;

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
    public void setMapLanguages() {
        supportedLanguageController.setMapLanguages(loadTranslationLanguages());
    }

    public void setLanguageTagSelected(String languageTagSelected) {
        this.languageTagSelected = languageTagSelected;
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageController.getSupportedLanguagesTags();
    }

    @Override
    public HashMap<String, String> getMapLanguages() {
        return supportedLanguageController.getMapLanguages(languageTagSelected);
    }

    @Override
    public String getTranslation(String key) {
        return getMapLanguages().get(key);
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

    private HashMap<String, HashMap<String, String>> loadTranslationLanguages() {
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

}
