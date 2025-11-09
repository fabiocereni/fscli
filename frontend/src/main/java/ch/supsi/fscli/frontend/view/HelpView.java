package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;

public class HelpView implements IShow {

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

    private static HelpView myself;

    private HelpView() {}

    public static HelpView getInstance() {
        if (myself == null) {
            myself = new HelpView();
        }
        return myself;
    }

    @Override
    public void showMyView() {
        String commands = supportedLanguageModel.getTranslation("label.infoHelp");

        TextArea textArea = new TextArea(commands);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setPrefSize(600, 400);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(supportedLanguageModel.getTranslation("label.titleHelp"));
        alert.setHeaderText(supportedLanguageModel.getTranslation("label.headerTextHelp"));
        alert.getDialogPane().setContent(textArea);
        alert.setResizable(true);

        alert.showAndWait();

    }
}