package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.IQuitController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.event.ConfirmExitEvent;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.beans.PropertyChangeEvent;
import java.util.Optional;

@Singleton
public class QuitView extends AbstractView {

    @Inject
    private IQuitController quitController;

    @Inject
    private ISupportedLanguageController supportedLanguageController;

    public boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle(supportedLanguageController.getTranslation("label.confirmExit"));
        alert.setHeaderText(supportedLanguageController.getTranslation("label.headerTextExit"));
        alert.setContentText(supportedLanguageController.getTranslation("label.contentTextExit"));

        ButtonType buttonTypeYes = new ButtonType(
                supportedLanguageController.getTranslation("label.yes") ,
                ButtonBar.ButtonData.OK_DONE
        );
        ButtonType buttonTypeNo = new ButtonType(
                supportedLanguageController.getTranslation("label.no"),
                ButtonBar.ButtonData.CANCEL_CLOSE
        );

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();

        return result.isPresent() && result.get() == buttonTypeYes;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if(evt instanceof ConfirmExitEvent) {
            System.out.println("exit aborted picked-up");

            if(this.showConfirmation())
                this.quitController.confirmQuit();

        }
    }
}