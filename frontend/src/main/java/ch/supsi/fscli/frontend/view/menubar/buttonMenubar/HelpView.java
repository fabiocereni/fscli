package ch.supsi.fscli.frontend.view.menubar.buttonMenubar;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

@Singleton
public class HelpView implements IShow {

    @Inject
    private ISupportedLanguageController supportedLanguageController;


    @Override
    public void showMyView() {
        String commands = supportedLanguageController.getTranslation("label.infoHelp");

        TextArea textArea = new TextArea(commands);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(600, 400);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(supportedLanguageController.getTranslation("label.titleHelp"));
        alert.setHeaderText(supportedLanguageController.getTranslation("label.headerTextHelp"));
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);

        alert.showAndWait();

    }
}