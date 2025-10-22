package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;

public class QuitController implements IQuitController{

    private static QuitController mySelf;

    private IShow quitAlertView;

    public static QuitController getInstance() {
        if (mySelf == null) {
            mySelf = new QuitController();
        }
        return mySelf;
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        quitAlertView = eventHandlerInitializer.alertQuitView();
    }

    @Override
    public void showQuitView() {
        quitAlertView.showMyView();
    }
}
