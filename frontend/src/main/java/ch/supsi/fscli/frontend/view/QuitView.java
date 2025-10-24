package ch.supsi.fscli.frontend.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class QuitView implements IQuitView {

    private static QuitView myself;

    //private TranslationsController translationsController;

    public static QuitView getInstance() {
        if (myself == null) {
            myself = new QuitView();
        }
        return myself;
    }

//    public void initialize(TranslationsController translationsController){
//        this.translationsController = translationsController;
//    }


    @Override
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
}