package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class QuitView implements IQuitView {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static QuitView myself;

    public static QuitView getInstance() {
        if (myself == null) {
            myself = new QuitView();
        }
        return myself;
    }


    @Override
    public boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(supportedLanguageModel.getTranslation("label.confirmExit"));
        alert.setHeaderText(supportedLanguageModel.getTranslation("label.headerTextExit"));
        alert.setContentText(supportedLanguageModel.getTranslation("label.contentTextExit"));

        ButtonType buttonTypeYes = new ButtonType(
                supportedLanguageModel.getTranslation("label.yes") ,
                ButtonBar.ButtonData.OK_DONE
        );
        ButtonType buttonTypeNo = new ButtonType(
                supportedLanguageModel.getTranslation("label.no"),
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        return result.isPresent() && result.get() == buttonTypeYes;
    }
}