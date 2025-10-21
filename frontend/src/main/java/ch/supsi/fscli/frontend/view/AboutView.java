package ch.supsi.fscli.frontend.view;

import javafx.scene.control.Alert;

public class AboutView implements IShow {

    private static AboutView myself;

    //private TranslationsController translationsController;

    private AboutView() {}


    public static AboutView getInstance() {
        if (myself == null) {
            myself = new AboutView();
        }
        return myself;
    }

//    public void initialize(TranslationsController translationsController){
//        this.translationsController = translationsController;
//    }

    @Override
    public void showMyView() {
        Alert aboutDialog = new Alert(Alert.AlertType.INFORMATION);
//        aboutDialog.setTitle(translationsController.translate("menuitem.about"));
//        aboutDialog.setHeaderText(translationsController.translate("about.header"));
//        aboutDialog.setContentText(translationsController.translate("about.content"));
        aboutDialog.setTitle("ciao");
        aboutDialog.setHeaderText("cioo");
        aboutDialog.setContentText("ciao");
        aboutDialog.show();
    }
}
