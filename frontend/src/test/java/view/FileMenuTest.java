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
//        testOpenMenuItem();
        testSaveMenuItem();
    }

    public void createNew() {
        clickOn("#fileMenu");

        clickOn("#newMenuItem");
    }


    private void testNewButtonPressed() {
        step("file menu item...", () -> {

            createNew();

            clickOn("#fileMenu");

            MenuItem exitMenuItem = lookup("#exitMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            assertTrue(exitMenuItem.isVisible());
            assertFalse(exitMenuItem.isDisable());

            verifyThat("#enter", isVisible());
            verifyThat("#enter", isEnabled());
            verifyThat("#outputView", (TextInputControl t) -> t.getText().isEmpty());


//
//            verifyThat("#logView", (TextInputControl t) ->
//                    t.getText().contains("Lingua selezionata it_IT") &&
//                    t.getText().startsWith("FS creato con successo.")
//            );


        });
    }


//    private void testOpenMenuItem() {
//
//        IFSDataReaderController dataReaderController = Guice.createInjector(new ControllerModule()).getInstance(IFSDataReaderController.class);
//
//
//        ReaderView mockLoadingView = new ReaderView() {
//            @Override
//            public void showMyView() {
//                // simulazione del comportamento senza aprire dialog
//                dataReaderController.reader(new File("test.json"));
//            }
//        };
//
//        dataReaderController.set
//
//        FileMenuController controller =
//                injector.getInstance(FileMenuController.class);
//
//
//
//        step("file menu item...", () -> {
//            sleep(SLEEP_INTERVAL);
//
//            clickOn("#fileMenu");
//
//            MenuItem openMenuItem = lookup("#openMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
//            assertTrue(openMenuItem.isVisible());
//            assertFalse(openMenuItem.isDisable());
//
//
//
//        });
//    }


    private void testSaveMenuItem() {
        step("test save menu item...", () -> {



            // 1. Apri il menu File
            clickOn("#fileMenu");

            MenuItem newMenuItem = lookup("#newMenuItem").queryAs(ContextMenuContent.MenuItemContainer.class).getItem();
            clickOn(newMenuItem.getStyleableNode());

            clickOn("#fileMenu");
            // Aspetta che il SaveMenuItem appaia
            verifyThat("#saveMenuItem", isVisible());
            verifyThat("#saveMenuItem", isEnabled());
            // 2. Clicca su Save
            // Nota: Assicurati che l'azione di "save" qui non apra un FileChooser di sistema
            // che non è mockato/gestito da TestFX, altrimenti il test si bloccherà.
            clickOn("#saveMenuItem");

            // 3. Controlla lo stato del SaveMenuItem dopo l'azione
            // (Assumendo che lo stato non cambi se non ci sono modifiche da salvare)
            // Se il tuo sistema resetta lo stato a disabilitato dopo il salvataggio:
            clickOn("#fileMenu");
            verifyThat("#saveMenuItem", isVisible());
            verifyThat("#saveMenuItem", isDisabled()); // Verifica se è disabilitato (più specifico)

            // 4. Controlla il log
            // Nota: La stringa deve essere nel log
            verifyThat("#logView", (TextInputControl t) ->
                    t.getText().contains("File system salvato correttamente in")
            );

            // 5. Chiudi il menu
            clickOn("#fileMenu");
        });
    }




}
