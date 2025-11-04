package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.PreferencesView;

public class PreferencesController implements IPreferencesController {

    private static PreferencesController myself;
    private IShow preferencesView;

    //TODO usare classe che legge i dati dal file
    //private PreferencesBusinessInterface preferencesModel;

    private PreferencesController() {
    }

    public static PreferencesController getInstance() {
        if (myself == null) {
            myself = new PreferencesController();
        }
        return myself;
    }

//    public void initialize(PreferencesView preferencesView, PreferencesBusinessInterface preferencesModel){
//        this.preferencesView = preferencesView;
//        this.preferencesModel = preferencesModel;
//    }

//    public String getCurrentLanguage() {
//        return preferencesModel.getCurrentLanguage();
//    }
//
//    public int getCurrentLineSCount() {
//        return preferencesModel.getLinesCount();
//    }
//
//    public void setCurrentLanguage(String tagLan) {
//        preferencesModel.setCurrentLanguage(tagLan);
//    }
//
//    public void setCurrentLinesCount(int minesCount) {
//        preferencesModel.setLinesCount(minesCount);
//    }
//
//    public String getCurrentFont() {
//        return preferencesModel.getCurrentFont();
//    }
//
//    public int getCurrentFontSize() {
//        return preferencesModel.getCurrentFontSize();
//    }
//
//    public void setCurrentFont(String tagLan) {
//        preferencesModel.setCurrentLanguage(tagLan);
//    }
//
//    public int setFontSize(int size) {
//        return preferencesModel.setFontSize(size);
//    }


//    /**
//     * Return the value for the given key
//     *
//     * @param key
//     * @return String
//     */
//    public Object getPreference(String key) {
//        return this.preferencesModel.getPreference(key);
//    }

//    public void updateProperties() {
//    }

    @Override
    public void showPreferencesView() {
        preferencesView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        preferencesView = eventHandlerInitializer.preferencesView();
    }
}
