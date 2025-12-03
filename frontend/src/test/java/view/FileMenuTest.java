package view;

import com.sun.javafx.scene.control.ContextMenuContent;
import com.sun.javafx.scene.control.MenuBarButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.isVisible;

public class FileMenuTest extends AbstractMainGUITest {

    @Test
    public void walkThrough() {
        testNewButtonPressed();
    }


    private void testNewButtonPressed() {
        step("file menu item...", () -> {

            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");

            MenuItem newMenuItem = lookup("#newMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(newMenuItem.isVisible());
            assertFalse(newMenuItem.isDisable());

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(exitMenuItem.isVisible());
            assertFalse(exitMenuItem.isDisable());

            verifyThat("#enter", isVisible());
//            verifyThat("#enter", isEnabled());
//            verifyThat("#outputView", (TextInputControl t) -> t.getText().isEmpty());


//
//            verifyThat("#logView", (TextInputControl t) ->
//                    t.getText().contains("Lingua selezionata it_IT") &&
//                    t.getText().startsWith("FS creato con successo.")
//            );

            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");

        });
    }


    private void testOpenMenuItem() {
        step("file menu item...", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");

            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(openMenuItem.isVisible());
            assertFalse(openMenuItem.isDisable());



        });
    }




}
