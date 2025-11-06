package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.model.IPreferencesModel;
import ch.supsi.fscli.frontend.model.PreferencesModel;
import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.PreferencesView;

import java.io.FileOutputStream;
import java.io.IOException;

public class PreferencesController implements IPreferencesController {

    private static PreferencesController myself;
    private IShow preferencesView;
    private final IPreferencesModel preferencesModel = PreferencesModel.getInstance();

    private PreferencesController() {}

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
//
//    public void setCurrentLanguage(String tagLan) {
//        preferencesModel.setCurrentLanguage(tagLan);
//    }
//
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

    public int getOutputLines() {
        return preferencesModel.getOutputLines();
    }

    public void setOutputLines(int minesCount) {
        preferencesModel.setOutputLines(minesCount);
    }

    public void savePreferences() {
        preferencesModel.save();
    }

    @Override
    public void showPreferencesView() {
        preferencesView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        preferencesView = eventHandlerInitializer.preferencesView();
    }
}
