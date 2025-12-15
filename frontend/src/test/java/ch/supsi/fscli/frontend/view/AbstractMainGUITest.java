package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.MainFx;
import javafx.stage.Stage;
import org.junit.jupiter.api.BeforeEach; // Nota: BeforeAll statico non serve più per le properties
import org.testfx.framework.junit5.ApplicationTest;
import org.testfx.util.WaitForAsyncUtils;

import java.util.logging.Logger;

abstract public class AbstractMainGUITest extends ApplicationTest {

    protected static final int SLEEP_INTERVAL = 100;
    protected static final Logger LOGGER = Logger.getAnonymousLogger();
    protected int stepNo;
    protected Stage primaryStage;

    // --- MODIFICA CRITICA: BLOCCO STATICO ---
    // Questo viene eseguito PRIMA che JavaFX venga inizializzato.
    static {
        // Leggiamo le property, ma se siamo in dubbio forziamo valori sicuri per la CI
        // Se vuoi essere sicuro al 100%, togli gli if e forza tutto.

        String headlessVal = System.getProperty("headless", "false");
        // Piccolo trucco: se non è settato, controlliamo se siamo in ambiente CI (spesso settano la variabile CI=true)
        if (Boolean.parseBoolean(headlessVal) || System.getenv("CI") != null) {
            System.out.println("FORCING HEADLESS & SOFTWARE RENDERING IN STATIC BLOCK");

            // 1. Forza Rendering Software (evita BufferOverflow e crash Xvfb)
            System.setProperty("prism.order", "sw");
            System.setProperty("prism.text", "t2k");
            System.setProperty("java.awt.headless", "true");

            // 2. Configura TestFX
            System.setProperty("testfx.robot", "glass");
            System.setProperty("testfx.headless", "true");

            // 3. Opzioni extra per stabilità
            System.setProperty("prism.forceSW", "true");
            System.setProperty("prism.disableEGL", "true");
        }
    }

    protected void step(final String step, final Runnable runnable) {
        ++stepNo;
        LOGGER.info("STEP" + stepNo + ":" + step);
        runnable.run();
        LOGGER.info("STEP" + stepNo + ":" + "end");
    }

    @Override
    public void start(final Stage stage) {
        this.primaryStage = stage;

        final MainFx main = new MainFx();
        main.init();
        main.start(stage);

        // --- FIX PER "NODE NOT FOUND" ---
        // stage.toFront() a volte non basta in Linux/Xvfb.
        // requestFocus() aiuta il robot a "agganciare" la finestra.
        stage.toFront();
        stage.requestFocus();

        WaitForAsyncUtils.waitForFxEvents();
    }
}