package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.EventHandlerInitializer;
import ch.supsi.fscli.frontend.view.IQuitView;
import javafx.stage.Stage;
import ch.supsi.fscli.frontend.MainFx;

public class QuitController implements IQuitController {

    private static QuitController mySelf;

    private IQuitView quitView;

    public static QuitController getInstance() {
        if (mySelf == null) {
            mySelf = new QuitController();
        }
        return mySelf;
    }

    @Override
    public void initialize(EventHandlerInitializer eventHandlerInitializer) {
        quitView = eventHandlerInitializer.quitView();
    }

    @Override
    public boolean showQuitView() {
        boolean confirmed = quitView.showConfirmation();
         if(confirmed)
             MainFx.getStageToClose().stream().toList().forEach(Stage::close);
         return confirmed;
    }
}
