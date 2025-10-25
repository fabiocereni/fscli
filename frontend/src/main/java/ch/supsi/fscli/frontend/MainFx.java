package ch.supsi.fscli.frontend;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.view.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashSet;

public class MainFx extends Application {

    public static Collection<Stage> stageToClose = new HashSet<>();

    public static Collection<Stage> getStageToClose() {
        return stageToClose;
    }

    private static final int PREF_INSETS_SIZE = 7;
    private static final int PREF_COMMAND_SPACER_WIDTH = 11;
    private static final int COMMAND_LINE_PREF_COLUMN_COUNT = 72;
    private static final int PREF_OUTPUT_VIEW_ROW_COUNT = 25;
    private static final int PREF_LOG_VIEW_ROW_COUNT = 5;

    private final String applicationTitle;

    private final MenuBarView menuBarView;
    private final CommandLineView commandLineView;
    private final OutputView outputView;
    private final LogView logView;
    private final IShow savingView;
    private final IQuitView quitView;

    private final EventHandlerInitializer eventHandlerInitializer;

    private final EventHandler dataSaverController;
    private final IQuitController quitController;

    public MainFx() {
        this.applicationTitle = "filesystem command interpreter simulator";

        // declaration
        this.menuBarView = MenuBarView.getInstance();
        this.commandLineView = CommandLineView.getInstance();
        this.outputView = OutputView.getInstance();
        this.logView = LogView.getInstance();
        this.savingView = SaveAsView.getInstance();
        this.quitView = QuitView.getInstance();

        this.eventHandlerInitializer = new EventHandlerInitializer(this.savingView, this.quitView);

        this.dataSaverController = FSDataSaverController.getInstance();
        this.quitController = QuitController.getInstance();

    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        // init
        this.menuBarView.initMenuBarView();
        this.commandLineView.initCommandLineView(COMMAND_LINE_PREF_COLUMN_COUNT);
        this.outputView.initOutputView(PREF_OUTPUT_VIEW_ROW_COUNT);
        this.logView.initLogView(PREF_LOG_VIEW_ROW_COUNT);


        this.dataSaverController.initialize(eventHandlerInitializer);
        this.quitController.initialize(eventHandlerInitializer);


        // horizontal box to hold the command line
        HBox commandLinePane = new HBox();
        commandLinePane.setAlignment(Pos.BASELINE_LEFT);
        commandLinePane.setPadding(new Insets(PREF_INSETS_SIZE));

        Region spacer1 = new Region();
        spacer1.setPrefWidth(PREF_COMMAND_SPACER_WIDTH);

        Region spacer2 = new Region();
        spacer2.setPrefWidth(PREF_COMMAND_SPACER_WIDTH);

        commandLinePane.getChildren().add(this.commandLineView.getCommandLineLabel());
        commandLinePane.getChildren().add(spacer1);
        commandLinePane.getChildren().add(this.commandLineView.getCommandLine());
        commandLinePane.getChildren().add(spacer2);
        commandLinePane.getChildren().add(this.commandLineView.getEnter());

        // vertical pane to hold the menu bar and the command line
        VBox top = new VBox(
                this.menuBarView.getNode(),
                commandLinePane
        );

        // scroll pane to hold the output view
        ScrollPane centerPane = new ScrollPane();
        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        centerPane.setPadding(new Insets(PREF_INSETS_SIZE));
        centerPane.setContent(this.outputView.getNode());



        // scroll pane to hold log view
        ScrollPane bottomPane = new ScrollPane();
        bottomPane.setFitToHeight(true);
        bottomPane.setFitToWidth(true);
        bottomPane.setPadding(new Insets(PREF_INSETS_SIZE));
        bottomPane.setContent(this.logView.getNode());

        // root pane
        BorderPane rootPane = new BorderPane();
        rootPane.setTop(top);
        rootPane.setCenter(centerPane);
        rootPane.setBottom(bottomPane);

        // scene
        Scene mainScene = new Scene(rootPane);

        // put the scene onto the primary stage
        primaryStage.setTitle(this.applicationTitle);
        primaryStage.setResizable(true);
        primaryStage.setScene(mainScene);

        // on close
        primaryStage.setOnCloseRequest(e -> {
            // send a command to the ApplicationExitController
            // to handle to exit process...
            //
            // for new we just close the app directly
            boolean confirmed = this.quitController.showQuitView();
            if(!confirmed)
                e.consume();
        });

        stageToClose.add(primaryStage);
        // show the primary stage
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception {
        // Posso gestire il salvataggio dei dati nel caso in cui l'app non sia chiusa correttamente.
        System.out.println("Dati non salvati!!!");
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}