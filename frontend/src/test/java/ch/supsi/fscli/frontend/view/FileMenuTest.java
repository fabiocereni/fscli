package ch.supsi.fscli.frontend.view;

import com.sun.javafx.scene.control.ContextMenuContent;

import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class FileMenuTest extends AbstractMainGUITest {

    public void createNew() {
        sleep(SLEEP_INTERVAL);
        clickOn("#fileMenu");
        sleep(SLEEP_INTERVAL);
        clickOn("#newMenuItem");
        sleep(SLEEP_INTERVAL);
    }

    @Test
    public void testNewButtonPressed() {
        step("Test creazione nuovo FS...", () -> {
            createNew();

            clickOn("#fileMenu");

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(exitMenuItem.isVisible());
            assertFalse(exitMenuItem.isDisable());

            sleep(SLEEP_INTERVAL);


            clickOn("#fileMenu");

            verifyThat("#enter", isVisible());
            verifyThat("#enter", isEnabled());
            verifyThat("#outputView", (TextInputControl t) -> t.getText().isEmpty());
            verifyThat("#logView", (TextInputControl t) ->
                                        t.getText().contains("FS creato con successo.") ||
                                        t.getText().contains("FS created successfully.") ||
                                        t.getText().contains("Dateisystem erfolgreich erstellt."));
        });
    }
    @Test
    public void testOpenMenuItem() {
        step("Test Open...", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");

            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(openMenuItem.isVisible());
            assertFalse(openMenuItem.isDisable());

            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");
        });
    }
    @Test
    public void testSaveAsMenuItem() {
        step("Test Save As...", () -> {
            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");
            MenuItem saveAsItem = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(saveAsItem.isVisible());
            assertTrue(saveAsItem.isDisable());
            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");


            createNew();
            sleep(SLEEP_INTERVAL);

            clickOn("#fileMenu");
            MenuItem saveAsItemEnabled = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertFalse(saveAsItemEnabled.isDisable());

            sleep(SLEEP_INTERVAL);

            clickOn("#fileMenu");
        });
    }

    @Test
    public void testSaveMenuItem() {
        step("test save menu item...", () -> {
             createNew();

            clickOn("#fileMenu");
            sleep(SLEEP_INTERVAL);
            verifyThat("#saveMenuItem", isVisible());

            clickOn("#saveMenuItem");

            sleep(SLEEP_INTERVAL);
            verifyThat("#logView", (TextInputControl t) ->
                    t.getText().contains("File system salvato correttamente in") ||
                            t.getText().contains("FS saved successfully in") ||
                            t.getText().contains("Dateisystem erfolgreich gespeichert in")
            );
        });
    }

    @Test
    public void testExitMenuItem() {
        step("Test voce menu Exit...", () -> {
            clickOn("#fileMenu");
            sleep(SLEEP_INTERVAL);
            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();

            assertTrue(exitMenuItem.isVisible(), "Il menu Exit dovrebbe essere visibile");
            assertFalse(exitMenuItem.isDisable(), "Il menu Exit dovrebbe essere sempre abilitato");
            sleep(SLEEP_INTERVAL);
            clickOn("#fileMenu");
        });
    }
}