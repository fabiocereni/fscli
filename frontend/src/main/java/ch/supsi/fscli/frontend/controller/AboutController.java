package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.AboutView;
import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

public class AboutController implements IAboutView {

    private static AboutController mySelf;

    private IShow aboutView;

    public AboutController(){};

    public static AboutController getInstance() {
        if (mySelf == null) {
            mySelf = new AboutController();
        }
        return mySelf;
    }

    @Override
    public void showAboutView() {
        aboutView.showMyView();
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        aboutView = eventHandlerInitializer.aboutView();
    }
}
