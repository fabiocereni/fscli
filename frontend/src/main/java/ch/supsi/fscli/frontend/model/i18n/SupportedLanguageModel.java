package ch.supsi.fscli.frontend.model.i18n;

import ch.supsi.fscli.backend.application.i18n.ISupportedLanguageApplication;
import ch.supsi.fscli.backend.application.i18n.SupportedLanguageApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.util.*;

@Singleton
public class SupportedLanguageModel implements ISupportedLanguageModel {

    private String languageTagSelected;

    @Inject
    private final ISupportedLanguageApplication supportedLanguageApplication;


    private static final String supportedLanguagePropertiesPath = "/supported-languages.properties";
    private static final String translationPropertiesLabel = "i18n.labels";

    @Inject
    public SupportedLanguageModel(ISupportedLanguageApplication supportedLanguageApplication) {
        this.supportedLanguageApplication = supportedLanguageApplication;
    }


    @Override
    public void setSupportedLanguagesTags() {
        supportedLanguageApplication.setSupportedLanguagesTags(loadSupportedLanguages());
    }

    @Override
    public List<String> getSupportedLanguagesTags() {
        return supportedLanguageApplication.getSupportedLanguagesTags();
    }

    @Override
    public void setMapLanguages() {
        supportedLanguageApplication.setMapLanguages(loadTranslationLanguages());
    }

    @Override
    public void setLanguageTagSelected(String languageTagSelected) {
        this.languageTagSelected = languageTagSelected;
    }

    @Override
    public String getLanguageTagSelected() {
        return languageTagSelected;
    }

    @Override
    public HashMap<String, String> getMapLanguages() {
        return supportedLanguageApplication.getMapLanguages(languageTagSelected);
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
            // Convert tags like en_US into a proper Locale to resolve the right bundle
            Locale currentLocale = Locale.forLanguageTag(tag.replace('_', '-'));

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
