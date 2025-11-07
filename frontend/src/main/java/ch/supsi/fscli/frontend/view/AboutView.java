package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.BuildInfoController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Alert;

@Singleton
public class AboutView implements IShow {

    private static AboutView myself;

    //private TranslationsController translationsController;
    private final BuildInfoController buildInfoController;

    @Inject
    public AboutView(BuildInfoController buildInfoController) {
        this.buildInfoController = buildInfoController;
    }


//    public void initialize(TranslationsController translationsController){
//        this.translationsController = translationsController;
//    }

    @Override
    public void showMyView() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle( "SUPSI FileSystem"); //TODO TRADUZIONE
        aboutDialog.setHeaderText("PROGETTO"); //TODO TRADUZIONE
        aboutDialog.setContentText(buildInfoController.getVersion());
        aboutDialog.show();
    }
}
