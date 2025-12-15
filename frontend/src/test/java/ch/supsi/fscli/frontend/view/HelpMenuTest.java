package ch.supsi.fscli.frontend.view;

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

            sleep(SLEEP_INTERVAL);
            clickOn("#helpMenuItem");
            sleep(SLEEP_INTERVAL);
            verifyThat("#helpTextArea", isVisible());


            verifyThat("#helpTextArea", TextInputControlMatchers.hasText(containsString("pwd")));

            press(KeyCode.ENTER).release(KeyCode.ENTER);
        });
    }

    @Test
    public void testAboutContent() {
        step("Test apertura e contenuto About...", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#helpMenu");
            sleep(SLEEP_INTERVAL);
            clickOn("#aboutMenuItem");
            sleep(SLEEP_INTERVAL);
            verifyThat("#aboutDialogPane", isVisible());
            sleep(SLEEP_INTERVAL);
            press(KeyCode.ENTER).release(KeyCode.ENTER);
        });
    }

}
