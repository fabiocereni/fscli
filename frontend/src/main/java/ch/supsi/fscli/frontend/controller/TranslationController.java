package ch.supsi.fscli.frontend.controller;

public class TranslationController {

    private static TranslationController myself;

    //private TraslationsBusinessInterface translationsModel;

    protected TranslationController() {}

//    public void initialize(PreferencesBusinessInterface preferencesModel, TraslationsBusinessInterface translationsModel){
//        this.preferencesModel = preferencesModel;
//        this.translationsModel = translationsModel;
//        this.translationsModel.changeLanguage(this.preferencesModel.getPreference("language-tag").toString());
//    }

    public static TranslationController getInstance() {
        if (myself == null) {
            myself = new TranslationController();
        }

        return myself;
    }

//    public List<String> getTagLanguages(){
//        return translationsModel.getSupportedLanguageTags();
//    }
//
//    /**
//     * Translate the given key
//     *
//     * @param key
//     *
//     * @return String
//     */
//    public String translate(String key) {
//        return this.translationsModel.translate(key);
//    }

}

