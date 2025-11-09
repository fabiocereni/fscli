package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.IQuitController;
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

    public boolean showConfirmation() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Conferma Uscita");
        alert.setHeaderText("Sei sicuro di voler uscire?");
        alert.setContentText("Qualsiasi modifica non salvata andrà persa.");

        ButtonType buttonTypeYes = new ButtonType(
                "Sì" ,
                ButtonBar.ButtonData.OK_DONE
        );
        ButtonType buttonTypeNo = new ButtonType(
                "No",
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