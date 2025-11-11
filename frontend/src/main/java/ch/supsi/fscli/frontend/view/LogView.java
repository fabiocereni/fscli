package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import ch.supsi.fscli.frontend.model.preference.IPreferencesModel;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

@Singleton
public class LogView {

    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;

    private String fontLog;

    private TextArea logView;

    @Inject
    public void init() {
        fontLog = preferencesController.getProperty(PreferencesModel.KEY_FONT_LOG_AREA);

        this.logView = new TextArea();
        this.logView.setId("logView");
        this.logView.appendText( supportedLanguageController.getTranslation("label.textLog") + "\n");
        this.logView.setStyle("-fx-font-family: " + fontLog + ";");
    }


    public void initLogView(int logViewPrefRowCount) {
        this.logView.setPrefRowCount(logViewPrefRowCount);
        this.logView.setEditable(false);
    }

    public Node getNode() {
        return this.logView;
    }
}