package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.*;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

@Singleton
public class MenuBarView {

    private final IFSDataSaverController dataSaverController;
    private final QuitController quitController;
    private final IAboutView aboutViewController;
    private final IHelpController helpController;
    private final IFSStateDirector fsStateDirector;

    private final MenuBar menuBar;
    private final Menu fileMenu;
    private final Menu editMenu;
    private final Menu helpMenu;

    @Inject
    private MenuBarView (IFSDataSaverController dataSaverController,
                         QuitController quitController, IAboutView aboutViewController,
                         IHelpController helpController, IFSStateDirector fsStateDirector) {

        this.dataSaverController = dataSaverController;
        this.quitController = quitController;
        this.aboutViewController = aboutViewController;
        this.helpController = helpController;
        this.fsStateDirector = fsStateDirector;
        this.fileMenu = new Menu("File");
        this.editMenu = new Menu("Edit");
        this.helpMenu = new Menu("Help");
        this.menuBar = new MenuBar();
    }

    public void initMenuBarView() {
        // FILE MENU
        MenuItem newMenuItem = new MenuItem("New");
        newMenuItem.setId("newMenuItem");
        newMenuItem.setOnAction(actionEvent -> fsStateDirector.createFileSystem());

        MenuItem openMenuItem = new MenuItem("Open...");
        openMenuItem.setId("openMenuItem");

        MenuItem saveMenuItem = new MenuItem("Save");
        saveMenuItem.setId("saveMenuItem");
        saveMenuItem.setOnAction(actionEvent -> dataSaverController.save());
        saveMenuItem.disableProperty().bind(fsStateDirector.fileSystemCreatedProperty().not());

        MenuItem saveAsMenuItem = new MenuItem("Save as...");
        saveAsMenuItem.setId("saveAsMenuItem");
        saveAsMenuItem.setOnAction((actionEvent) -> dataSaverController.showSavingView());
        saveAsMenuItem.disableProperty().bind(fsStateDirector.fileSystemCreatedProperty().not());

        MenuItem exitMenuItem = new MenuItem("Exit...");
        exitMenuItem.setId("exitMenuItem");
        exitMenuItem.setOnAction((actionEvent) -> quitController.showQuitView());

        this.fileMenu.setId("fileMenu");
        this.fileMenu.getItems().add(newMenuItem);
        this.fileMenu.getItems().add(new SeparatorMenuItem());
        this.fileMenu.getItems().add(openMenuItem);
        this.fileMenu.getItems().add(saveMenuItem);
        this.fileMenu.getItems().add(saveAsMenuItem);
        this.fileMenu.getItems().add(new SeparatorMenuItem());
        this.fileMenu.getItems().add(exitMenuItem);

        // EDIT MENU
        MenuItem preferencesMenuItem = new MenuItem("Preferences...");
        preferencesMenuItem.setId("preferencesMenuItem");

        this.editMenu.setId("editMenu");
        this.editMenu.getItems().add(preferencesMenuItem);

        // HELP MENU
        MenuItem helpMenuItem = new MenuItem("Help");
        helpMenuItem.setId("helpMenuItem");
        helpMenuItem.setOnAction(actionEvent -> helpController.showHelpView());

        MenuItem aboutMenuItem = new MenuItem("About");
        aboutMenuItem.setId("aboutMenuItem");
        aboutMenuItem.setOnAction((actionEvent) -> aboutViewController.showAboutView());

        this.helpMenu.setId("helpMenu");
        this.helpMenu.getItems().add(helpMenuItem);
        this.helpMenu.getItems().add(aboutMenuItem);

        // MENU BAR
        this.menuBar.getMenus().addAll(fileMenu, editMenu, helpMenu);
    }

    public Node getNode() {
        return this.menuBar;
    }
}