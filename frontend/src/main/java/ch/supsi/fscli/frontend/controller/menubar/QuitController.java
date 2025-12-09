package ch.supsi.fscli.frontend.controller.menubar;

import ch.supsi.fscli.frontend.director.ConfirmExitDirector;

import ch.supsi.fscli.frontend.model.filesystem.IFSStateModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.Stage;
import ch.supsi.fscli.frontend.MainFx;

@Singleton
public class QuitController implements IQuitController {

    @Inject
    private IFSStateModel fsStateModel;
    @Inject
    private ConfirmExitDirector confirmExitDirector;


    @Override
    public boolean manageQuit() {

        if(fsStateModel.isCloseable()) {
            MainFx.getStageToClose().stream().toList().forEach(Stage::close);
            return true;
        } else {
            this.confirmExitDirector.manageExit();
            return false;
        }
    }

    @Override
    public void confirmQuit() {
        MainFx.getStageToClose().stream().toList().forEach(Stage::close);
    }

}