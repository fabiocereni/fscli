package ch.supsi.fscli.frontend.view;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

public class QuitAlertView implements IShow {

    private static QuitAlertView myself;

    //private TranslationsController translationsController;

    public static QuitAlertView getInstance() {
        if (myself == null) {
            myself = new QuitAlertView();
        }
        return myself;
    }

//    public void initialize(TranslationsController translationsController){
//        this.translationsController = translationsController;
//    }

    @Override
    public void showMyView() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
//        alert.setTitle(translationsController.translate("quit.confirm.title"));
//        alert.setHeaderText(translationsController.translate("quit.confirm.header"));
//        alert.setContentText(translationsController.translate("quit.confirm.content"));

//        alert.getButtonTypes().setAll(
//                new ButtonType(translationsController.translate("quit.confirm.yes"), ButtonBar.ButtonData.OK_DONE),
//                new ButtonType(translationsController.translate("quit.confirm.no"), ButtonBar.ButtonData.CANCEL_CLOSE)
//        );

        // return alert.showAndWait().orElse(ButtonType.CANCEL); CHIEDERE COME FARE
    }

}