package view;

import javafx.scene.input.KeyCode;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class HelpMenuTest extends AbstractMainGUITest {

    @Test
    public void testHelpContent() {
        step("Test apertura e contenuto Help...", () -> {
            clickOn("#helpMenu");

            clickOn("#helpMenuItem");

            verifyThat("#helpTextArea", isVisible());


            verifyThat("#helpTextArea", TextInputControlMatchers.hasText(containsString("pwd")));

            press(KeyCode.ENTER).release(KeyCode.ENTER);
        });
    }

    @Test
    public void testAboutContent() {
        step("Test apertura e contenuto About...", () -> {
            clickOn("#helpMenu");

            clickOn("#aboutMenuItem");

            verifyThat("#aboutDialogPane", isVisible());

            press(KeyCode.ENTER).release(KeyCode.ENTER);
        });
    }

}
