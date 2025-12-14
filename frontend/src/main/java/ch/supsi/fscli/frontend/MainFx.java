package ch.supsi.fscli.frontend;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.menubar.*;
import ch.supsi.fscli.frontend.director.ConfirmExitDirector;
import ch.supsi.fscli.frontend.director.LogDirector;
import ch.supsi.fscli.frontend.model.preference.PreferencesModel;
import ch.supsi.fscli.frontend.modules.*;
import ch.supsi.fscli.frontend.view.*;
import ch.supsi.fscli.frontend.view.menubar.MenuBarView;
import ch.supsi.fscli.frontend.view.menubar.buttonMenubar.*;
import com.google.inject.Guice;
import com.google.inject.Inject;
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

    private static String applicationTitle = "filesystem command interpreter simulator";


    // View
    @Inject private MenuBarView menuBarView;
    @Inject private CommandLineView commandLineView;
    @Inject private OutputView outputView;
    @Inject private LogView logView;
    @Inject private QuitView quitView;

    @Inject private IQuitController quitController;

    // Directors
    @Inject private LogDirector logDirector;
    @Inject private ConfirmExitDirector confirmExitDirector;

    private BorderPane rootPane;


    @Override
    public void init() {
        Guice.createInjector(new MainModule()).injectMembers(this);

        commandLineView.setOutputView(outputView);

        this.confirmExitDirector.addPropertyChangeListener(this.quitView);
        this.logDirector.addPropertyChangeListener(this.logView);

        buildMainView();
    }



    private void buildMainView() {
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

        primaryStage.setTitle(applicationTitle);
        primaryStage.setResizable(false);
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
