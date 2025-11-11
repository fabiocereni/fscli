package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.BuildInfoController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Alert;

@Singleton
public class AboutView implements IShow {

    @Inject
    private ISupportedLanguageController supportedLanguageController;

    @Inject
    private BuildInfoController buildInfoController;


    @Override
    public void showMyView() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle(supportedLanguageController.getTranslation("label.titleAbout"));
        aboutDialog.setHeaderText(supportedLanguageController.getTranslation("label.headerTextAbout"));
        aboutDialog.setContentText(buildInfoController.getVersion());
        aboutDialog.show();
    }
}