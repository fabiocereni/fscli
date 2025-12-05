package ch.supsi.fscli.frontend;

import ch.supsi.fscli.backend.modules.FileSystemModule;
import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.persistence.FSDataReaderController;
import ch.supsi.fscli.frontend.controller.persistence.FSDataSaverController;
import ch.supsi.fscli.frontend.controller.filesystem.creation.FSCreationController;
import ch.supsi.fscli.frontend.controller.filesystem.creation.IFSCreationController;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.i18n.SupportedLanguageController;
import ch.supsi.fscli.frontend.controller.menubar.AboutController;
import ch.supsi.fscli.frontend.controller.menubar.HelpController;
import ch.supsi.fscli.frontend.controller.menubar.IQuitController;
import ch.supsi.fscli.frontend.controller.menubar.QuitController;
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
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Collection;
import java.util.HashSet;

public class MainFx extends Application {

    public static Collection<Stage> stageToClose = new HashSet<>();
    public static Collection<Stage> getStageToClose() { return stageToClose; }

    private static final int PREF_INSETS_SIZE = 7;
    private static final int PREF_COMMAND_SPACER_WIDTH = 11;
    private static final int COMMAND_LINE_PREF_COLUMN_COUNT = 72;
    private static final int PREF_OUTPUT_VIEW_ROW_COUNT = 25;
    private static final int PREF_LOG_VIEW_ROW_COUNT = 5;

    private String applicationTitle = "filesystem command interpreter simulator";

    private Injector injector;

    // View
    private MenuBarView menuBarView;
    private CommandLineView commandLineView;
    private OutputView outputView;
    private LogView logView;
    private IShow savingView;
    private IShow readerView;
    private QuitView quitView;
    private IShow aboutView;
    private IShow helpView;
    private PreferencesView preferencesView;

    // Controllers
    private IFSDataSaverController dataSaverController;
    private IFSDataReaderController dataReaderController;
    private IAboutController aboutViewController;
    private IHelpController helpController;
    private IQuitController quitController;

    // Directors
    private WidgetDirector widgetDirector;
    private LogDirector logDirector;
    private ConfirmExitDirector confirmExitDirector;

    // State
    private IFSCreationController fsStateDirector;
    private ISupportedLanguageController supportedLanguageController;
    private IPreferencesController preferencesController;
    private PreferencesModel preferencesModel;

    private BorderPane rootPane;



    @Override
    public void init() {

        this.injector = Guice.createInjector(
                new ViewModule(),
                new ControllerModule(),
                new DirectorModule(),
                new ModelModule(),
                new FileSystemModule()
        );

        // Models & preferences
        this.preferencesController = injector.getInstance(PreferencesController.class);
        this.supportedLanguageController = injector.getInstance(SupportedLanguageController.class);
        this.preferencesModel = injector.getInstance(PreferencesModel.class);

        supportedLanguageController.setSupportedLanguagesTags();
        supportedLanguageController.setMapLanguages();
        supportedLanguageController.setLanguageTagSelected(
                preferencesController.getProperty(PreferencesModel.KEY_LANGUAGE)
        );

        // Views
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

        commandLineView.setOutputView(outputView);

        // Controllers
        this.dataSaverController = injector.getInstance(FSDataSaverController.class);
        this.dataReaderController = injector.getInstance(FSDataReaderController.class);
        this.quitController = injector.getInstance(QuitController.class);
        this.aboutViewController = injector.getInstance(AboutController.class);
        this.helpController = injector.getInstance(HelpController.class);

        // Directors
        this.fsStateDirector = injector.getInstance(FSCreationController.class);
        this.widgetDirector = injector.getInstance(WidgetDirector.class);
        this.logDirector = injector.getInstance(LogDirector.class);
        this.confirmExitDirector = injector.getInstance(ConfirmExitDirector.class);

        this.confirmExitDirector.addPropertyChangeListener(this.quitView);
        this.logDirector.addPropertyChangeListener(this.logView);



        buildMainView();
    }



    private void buildMainView() {

        this.menuBarView.init();
        this.menuBarView.initMenuBarView();
        this.commandLineView.initCommandLineView(COMMAND_LINE_PREF_COLUMN_COUNT);
        this.outputView.initOutputView(PREF_OUTPUT_VIEW_ROW_COUNT);
        this.logView.initLogView(PREF_LOG_VIEW_ROW_COUNT);

        // command line
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

        VBox top = new VBox(
                this.menuBarView.getNode(),
                commandLinePane
        );

        ScrollPane centerPane = new ScrollPane();
        centerPane.setFitToHeight(true);
        centerPane.setFitToWidth(true);
        centerPane.setPadding(new Insets(PREF_INSETS_SIZE));
        centerPane.setContent(this.outputView.getNode());

        ScrollPane bottomPane = new ScrollPane();
        bottomPane.setFitToHeight(true);
        bottomPane.setFitToWidth(true);
        bottomPane.setPadding(new Insets(PREF_INSETS_SIZE));
        bottomPane.setContent(this.logView.getNode());

        rootPane = new BorderPane();
        rootPane.setTop(top);
        rootPane.setCenter(centerPane);
        rootPane.setBottom(bottomPane);
    }



    @Override
    public void start(Stage primaryStage) {




        Scene mainScene = new Scene(this.rootPane);

        primaryStage.setTitle(this.applicationTitle);
        primaryStage.setResizable(true);
        primaryStage.setScene(mainScene);

        primaryStage.setOnCloseRequest(e -> {
            boolean confirmed = this.quitController.manageQuit();
            if (confirmed)
                e.consume();
        });

        stageToClose.add(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        Application.launch(args);
    }
}
