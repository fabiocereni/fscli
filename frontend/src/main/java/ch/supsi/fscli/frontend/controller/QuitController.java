package ch.supsi.fscli.frontend.controller;

import ch.supsi.fscli.frontend.view.IQuitView;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import ch.supsi.fscli.frontend.MainFx;

@Singleton
public class QuitController implements IQuitController {

    private final IQuitView quitView;

    @Inject
    public QuitController(IQuitView quitView) {
        this.quitView = quitView;
    }

    @Override
    public boolean showQuitView() {
        boolean confirmed = quitView.showConfirmation();
         if(confirmed)
             MainFx.getStageToClose().stream().toList().forEach(Stage::close);
         return confirmed;
    }
}
