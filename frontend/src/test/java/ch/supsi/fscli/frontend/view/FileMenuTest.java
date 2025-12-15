package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;
import ch.supsi.fscli.frontend.modules.ControllerModule;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.sun.javafx.scene.control.ContextMenuContent;
import javafx.scene.Node;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputControl;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testfx.util.WaitForAsyncUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.testfx.api.FxAssert.verifyThat;
import static org.testfx.matcher.base.NodeMatchers.*;

public class FileMenuTest extends AbstractMainGUITest {

    public void createNew() {
        clickOn("#fileMenu");
        clickOn("#newMenuItem");
    }

    @Test
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
            verifyThat("#logView", (TextInputControl t) -> t.getText().contains("FS Creato con successo.") ||
                    t.getText().contains("FS created successfully.") ||
                    t.getText().contains("Dateisystem erfolgreich erstellt."));
        });
    }
    @Test
    public void testOpenMenuItem() {
        step("Test Open...", () -> {
            clickOn("#fileMenu");

            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(openMenuItem.isVisible());
            assertFalse(openMenuItem.isDisable());


            clickOn("#fileMenu");
        });
    }
    @Test
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

    @Test
    public void testSaveMenuItem() {
        step("test save menu item...", () -> {
            // Assumiamo che createNew() sia già stato chiamato dai test precedenti o lo chiamiamo qui
             createNew();

            clickOn("#fileMenu");
            verifyThat("#saveMenuItem", isVisible());
            // Se è stato creato un FS, dovrebbe essere attivo (o disattivo se appena salvato, dipende dalla logica precisa)
            // Nel WidgetDirector: saveMenuItem si abilita con InputEvent o FilesystemCreatedEvent

            // Qui clicchiamo save perché FSDataSaverController.save() (senza path)
            // NON apre un dialog se il path è già impostato o gestito internamente,
            // MA attenzione: la prima volta potrebbe comportarsi come Save As se non c'è un path.
            // Nel tuo codice FSDataSaverModel usa un path di default con timestamp, quindi non dovrebbe aprire dialog.
            clickOn("#saveMenuItem");

            // Verifica output nel log
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
            // 1. Apri il menu File
            clickOn("#fileMenu");

            // 2. Recupera l'elemento Exit
            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();

            // 3. Verifiche: deve essere visibile e abilitato (si può sempre uscire)
            assertTrue(exitMenuItem.isVisible(), "Il menu Exit dovrebbe essere visibile");
            assertFalse(exitMenuItem.isDisable(), "Il menu Exit dovrebbe essere sempre abilitato");

            // 4. Chiudi il menu senza cliccare (per evitare di chiudere l'app di test)
            clickOn("#fileMenu");
        });
    }
}