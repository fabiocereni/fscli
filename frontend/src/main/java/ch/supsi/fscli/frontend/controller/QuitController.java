package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IShow;
import ch.supsi.fscli.frontend.view.QuitAlertView;

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

    public void handleQuit() {
        ((QuitAlertView) quitAlertView).setOnConfirm(this::quit);
        quitAlertView.showMyView();
    }

    public void quit() {
        System.out.println("QUIT");
        // Logica effettiva di chiusura dell'app (finestra)

    }
}
