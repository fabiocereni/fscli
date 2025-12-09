package view;

import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class FileMenuTest extends AbstractMainGUITest {

    @Test
    public void walkThrough() {
        testNewButtonPressed();
        testOpenMenuItem();
        testSaveAsMenuItem();
        testSaveMenuItem();
        testExitMenuItem();
    }

    public void createNew() {
        clickOn("#fileMenu");
        clickOn("#newMenuItem");
    }


    public void testNewButtonPressed() {
        step("Test creazione nuovo FS...", () -> {
            createNew();

            clickOn("#fileMenu");

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(exitMenuItem.isVisible());
            assertFalse(exitMenuItem.isDisable());


            clickOn("#fileMenu");

            verifyThat("#enter", isVisible());
            verifyThat("#enter", isEnabled());
            verifyThat("#outputView", (TextInputControl t) -> t.getText().isEmpty());
            verifyThat("#logView", (TextInputControl t) -> t.getText().contains("FS creato con successo."));
        });
    }

    public void testOpenMenuItem() {
        step("Test Open...", () -> {
            clickOn("#fileMenu");

            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(openMenuItem.isVisible());
            assertFalse(openMenuItem.isDisable());


            clickOn("#fileMenu");
        });
    }

    public void testSaveAsMenuItem() {
        step("Test Save As...", () -> {

            clickOn("#fileMenu");
            MenuItem saveAsItem = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(saveAsItem.isVisible());
            assertTrue(saveAsItem.isDisable());
            clickOn("#fileMenu");


            createNew();


            clickOn("#fileMenu");
            MenuItem saveAsItemEnabled = lookup("#saveAsMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertFalse(saveAsItemEnabled.isDisable());

            clickOn("#fileMenu");
        });
    }

    public void testSaveMenuItem() {
        step("test save menu item...", () -> {
             createNew();

            clickOn("#fileMenu");
            verifyThat("#saveMenuItem", isVisible());


            verifyThat("#logView", (TextInputControl t) ->
                    t.getText().contains("File system salvato correttamente in")
            );
        });
    }

    public void testExitMenuItem() {
        step("Test voce menu Exit...", () -> {
            clickOn("#fileMenu");

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();

            assertTrue(exitMenuItem.isVisible(), "Il menu Exit dovrebbe essere visibile");
            assertFalse(exitMenuItem.isDisable(), "Il menu Exit dovrebbe essere sempre abilitato");


            clickOn("#fileMenu");
        });
    }
}