package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.AboutController;
import ch.supsi.fscli.frontend.controller.BuildInfoController;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.control.Alert;

public class AboutView implements IShow {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static AboutView myself;

    private BuildInfoController buildInfoController = BuildInfoController.getInstance();

    private AboutView() {}


    public static AboutView getInstance() {
        if (myself == null) {
            myself = new AboutView();
        }
        return myself;
    }

    @Override
    public void showMyView() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
        aboutDialog.setTitle(supportedLanguageModel.getTranslation("label.titleAbout"));
        aboutDialog.setHeaderText(supportedLanguageModel.getTranslation("label.headerTextAbout"));
        aboutDialog.setContentText(buildInfoController.getVersion());
        aboutDialog.show();
    }
}
