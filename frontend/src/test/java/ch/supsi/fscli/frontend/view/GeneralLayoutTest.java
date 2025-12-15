package ch.supsi.fscli.frontend.view;


import com.sun.javafx.scene.control.ContextMenuContent;
import com.sun.javafx.scene.control.MenuBarButton;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.Test;
import org.testfx.matcher.control.TextInputControlMatchers;

import static org.junit.jupiter.api.Assertions.*;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class GeneralLayoutTest extends AbstractMainGUITest {

    @Test
    public void testMainScene() {
        step("main scene...", () -> {
            sleep(SLEEP_INTERVAL);
            verifyThat("#fileMenu", isVisible());
            verifyThat("#editMenu", isVisible());
            verifyThat("#helpMenu", isVisible());
            verifyThat("#commandLineView", isVisible());
            verifyThat("#commandLineView", isDisabled());
            verifyThat("#commandLineView", TextInputControlMatchers.hasText(""));
            verifyThat("#enter", isVisible());
            verifyThat("#enter", isDisabled());
            verifyThat("#outputView", (TextInputControl t) ->
                    t.getText().contains("Questo e' un esempio del testo in output...") ||
                            t.getText().contains("This is an example output text...") ||
                            t.getText().contains("Dies ist ein Beispiel-Ausgabetext...")
            );
            sleep(SLEEP_INTERVAL);

            verifyThat("#logView", (TextInputControl t) ->
                    t.getText().contains("Lingua selezionata it_IT") ||
                            t.getText().contains("Language selected en_US") ||
                            t.getText().contains("Sprache ausgewahlt de_CH"));
        });
    }


    @Test
    public void testFileMenu() {
        step("file menu...", () -> {
            Menu menu = lookup("#fileMenu").queryAs(MenuBarButton.class).menu;
            assertTrue(menu.isVisible());
            assertFalse(menu.isDisable());

            sleep(SLEEP_INTERVAL);

            clickOn("#fileMenu");

            sleep(SLEEP_INTERVAL);


            MenuItem newMenuItem = lookup("#newMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(newMenuItem.isVisible());
            assertFalse(newMenuItem.isDisable());


            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(openMenuItem.isVisible());
            assertFalse(openMenuItem.isDisable());


            MenuItem saveMenuItem = lookup("#saveMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(saveMenuItem.isVisible());
            assertTrue(saveMenuItem.isDisable());

            MenuItem saveAsMenuItem = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(saveAsMenuItem.isVisible());
            assertTrue(saveAsMenuItem.isDisable());

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(exitMenuItem.isVisible());
            assertFalse(exitMenuItem.isDisable());

            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");
        });
    }

    @Test
    public void testEditMenu() {
        step("edit menu...", () -> {
            Menu menu = lookup("#editMenu").queryAs(MenuBarButton.class).menu;
            assertTrue(menu.isVisible());
            assertFalse(menu.isDisable());

            sleep(SLEEP_INTERVAL);

            clickOn("#editMenu");

            sleep(SLEEP_INTERVAL);
            MenuItem preferencesMenuItem = lookup("#preferencesMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(preferencesMenuItem.isVisible());
            assertFalse(preferencesMenuItem.isDisable());

            sleep(SLEEP_INTERVAL);

            clickOn("#editMenu");
        });
    }
    @Test
    public void testHelpMenu() {
        step("help menu...", () -> {

            Menu menu = lookup("#helpMenu").queryAs(MenuBarButton.class).menu;
            assertTrue(menu.isVisible());
            assertFalse(menu.isDisable());

            sleep(SLEEP_INTERVAL);

            clickOn("#helpMenu");

            sleep(SLEEP_INTERVAL);

            MenuItem helpMenuItem = lookup("#helpMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(helpMenuItem.isVisible());
            assertFalse(helpMenuItem.isDisable());

            MenuItem aboutMenuItem = lookup("#aboutMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(aboutMenuItem.isVisible());
            assertFalse(aboutMenuItem.isDisable());

            sleep(SLEEP_INTERVAL);
            clickOn("#helpMenu");
        });
    }
}