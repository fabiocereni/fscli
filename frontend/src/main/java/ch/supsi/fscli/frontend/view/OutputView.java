package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

@Singleton
public class OutputView {

    @Inject
    private IPreferencesModel preferencesModel;
    @Inject
    private ISupportedLanguageController supportedLanguageController;

    @Inject
    private IPreferencesController preferencesController;

    private String fontOutput;

    private TextArea outputView;

    @Inject
    public void init() {
        fontOutput = preferencesController.getProperty(PreferencesModel.KEY_FONT_OUTPUT_AREA);

        this.outputView = new TextArea();
        this.outputView.setId("outputView");
        this.outputView.appendText(supportedLanguageController.getTranslation("label.textOutput") + "\n");
        this.outputView.setStyle("-fx-font-family: " + fontOutput + ";");
    }

    public void initOutputView(int prefRowCount) {
        outputView.setId("outputView");
        outputView.setEditable(false);
        outputView.setWrapText(true);
        outputView.setPrefRowCount(prefRowCount);
    }

    public Node getNode() {
        return this.outputView;
    }
}
