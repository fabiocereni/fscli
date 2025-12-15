package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.event.LogEvent;
import ch.supsi.fscli.frontend.event.SaveEvent;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.TextArea;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static ch.supsi.fscli.frontend.model.preference.PreferencesModel.DEFAULT_LINES_NUMBER_LOG;

@Singleton
public class LogView implements PropertyChangeListener {
    @Inject
    private IPreferencesController preferencesController;
    @Inject
    private ISupportedLanguageController supportedLanguageController;

    private String fontLog;

    private TextArea logView;

    public void init() {

        fontLog = preferencesController.getProperty(PreferencesModel.KEY_FONT_LOG_AREA);
        logView = new TextArea();
        this.logView.setPrefRowCount(Integer.parseInt(preferencesController.getProperty(PreferencesModel.KEY_LINES_NUMBER_LOG)));
        this.logView.setEditable(false);
        this.logView.setId("logView");
        this.logView.setStyle("-fx-font-family:" + fontLog + ";");
        this.logView.appendText(supportedLanguageController.getTranslation("label.langInfo") + " " + supportedLanguageController.getLanguageTagSelected());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt instanceof LogEvent logEvent) {
            if (logView != null) {
                String msg = logEvent.getMessage();
                String[] parts = msg.split("::", 2);
                String key = parts[0];
                String param = (parts.length > 1) ? parts[1] : "";

                String translated = supportedLanguageController.getTranslation(key);
                if (!param.isEmpty())
                    translated = translated + " " + param;
                logView.appendText("\n" + translated);
            }
        }
    }

    public Node getNode() {
        return this.logView;
    }

}