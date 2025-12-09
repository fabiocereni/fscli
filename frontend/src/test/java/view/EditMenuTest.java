package view;

import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;

import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isDisabled;
import static org.testfx.matcher.base.NodeMatchers.isEnabled;
import static org.testfx.util.NodeQueryUtils.isVisible;

public class EditMenuTest extends AbstractMainGUITest {


    @Test
    void walkThrough() {
        testPreferences();
    }


//    @Test
    public void testPreferences() {
        step("Apertura finestra preferenze...", () -> {
            clickOn("#editMenu");


            clickOn("#preferencesMenuItem");

            verifyThat("#languageComboBox", isVisible());
            verifyThat("#linesField", isVisible());


            verifyThat("#saveButton", isDisabled());
        });

        step("Modifica valori e abilitazione tasto Salva...", () -> {
            doubleClickOn("#linesField");
            TextField numberOfLines = lookup("#linesField").queryAs(TextField.class);

            String tmp = numberOfLines.getText();

            int num = Integer.parseInt(tmp);

            int numToWrite = (num/2) + 1;

            write(String.valueOf(numToWrite));

            press(KeyCode.TAB).release(KeyCode.TAB);

            verifyThat("#saveButton", isEnabled());
        });

        step("Salvataggio e chiusura...", () -> {
            clickOn("#saveButton");


            verifyThat("#logView", (TextInputControl t) ->
                    t.getText().contains("Preferenze salvate") ||
                            t.getText().contains("Preferences saved")
            );
        });
    }



}
