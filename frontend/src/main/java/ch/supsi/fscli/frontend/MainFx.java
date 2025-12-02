package ch.supsi.fscli.frontend;

import ch.supsi.fscli.backend.modules.FileSystemModule;
import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.controller.preference.PreferencesController;
import ch.supsi.fscli.frontend.director.ConfirmExitDirector;
import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.director.WidgetDirector;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import ch.supsi.fscli.frontend.modules.ControllerModule;
import ch.supsi.fscli.frontend.modules.DirectorModule;
import ch.supsi.fscli.frontend.modules.ModelModule;
import ch.supsi.fscli.frontend.modules.ViewModule;
import ch.supsi.fscli.frontend.view.*;
import com.google.inject.Guice;
import com.google.inject.Injector;
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
    private final IShow readerView;
    private final QuitView quitView;
    private final IShow aboutView;
    private final IShow helpView;
    private final PreferencesView preferencesView;

    private final Injector injector;

    private final EventHandler dataSaverController;
    private final EventHandler dataReaderController;
    private final EventHandler aboutViewController;
    private final EventHandler helpController;
    private final IQuitController quitController;

    private final WidgetDirector widgetDirector;
    private final LogDirector logDirector;
    private final ConfirmExitDirector confirmExitDirector;

    private final IFSCreationController fsStateDirector;
    private final ISupportedLanguageController supportedLanguageController;
    private final IPreferencesController preferencesController;

    private final PreferencesModel preferencesModel;

    public MainFx() {
        this.applicationTitle = "filesystem command interpreter simulator";

        this.injector = Guice.createInjector(new ViewModule(), new ControllerModule(),
                                             new DirectorModule(), new ModelModule(),
                                             new FileSystemModule());


        this.preferencesController = injector.getInstance(PreferencesController.class);
        this.supportedLanguageController = injector.getInstance(SupportedLanguageController.class);
        this.preferencesModel = injector.getInstance(PreferencesModel.class);
        this.supportedLanguageController.setSupportedLanguagesTags();
        this.supportedLanguageController.setMapLanguages();
        this.supportedLanguageController.setLanguageTagSelected(preferencesController.getProperty(PreferencesModel.KEY_LANGUAGE));

        // declaration
        this.preferencesView = injector.getInstance(PreferencesView.class);
        this.menuBarView = injector.getInstance(MenuBarView.class);
        this.commandLineView = injector.getInstance(CommandLineView.class);
        this.outputView = injector.getInstance(OutputView.class);
        this.logView = injector.getInstance(LogView.class);
        this.savingView = injector.getInstance(SaveAsView.class);
        this.readerView = injector.getInstance(ReaderView.class);
        this.aboutView = injector.getInstance(AboutView.class);
        this.helpView = injector.getInstance(HelpView.class);
        this.quitView = injector.getInstance(QuitView.class);


        commandLineView.setOutputView(outputView); // imposto l'output view sul command line view


        this.dataSaverController = injector.getInstance(FSDataSaverController.class);
        this.dataReaderController = injector.getInstance(FSDataReaderController.class);
        this.quitController = injector.getInstance(QuitController.class);
        this.aboutViewController = injector.getInstance(AboutController.class);
        this.helpController = injector.getInstance(HelpController.class);

        this.fsStateDirector = injector.getInstance(FSCreationController.class);
        this.widgetDirector = injector.getInstance(WidgetDirector.class);
        this.logDirector = injector.getInstance(LogDirector.class);
        this.confirmExitDirector = injector.getInstance(ConfirmExitDirector.class);

        this.confirmExitDirector.addPropertyChangeListener(this.quitView);
        this.logDirector.addPropertyChangeListener(this.logView);
    }

    @Override
    public void start(Stage primaryStage) {
        // init
        this.menuBarView.initMenuBarView();
        this.commandLineView.initCommandLineView(COMMAND_LINE_PREF_COLUMN_COUNT);
        this.outputView.initOutputView(PREF_OUTPUT_VIEW_ROW_COUNT);
        this.logView.initLogView(PREF_LOG_VIEW_ROW_COUNT);
        //this.aboutView.initialize(translationController);

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
            boolean confirmed = this.quitController.manageQuit();
            System.out.println(confirmed);
            if(confirmed)
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