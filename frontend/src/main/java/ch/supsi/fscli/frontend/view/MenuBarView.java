package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.*;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

import java.util.Collections;
import java.util.List;


public class MenuBarView {

    private static MenuBarView myself;

    private final IFSDataSaverController dataSaverController = FSDataSaverController.getInstance();
    private final QuitController quitController = QuitController.getInstance();
    private final IAboutView aboutViewController = AboutController.getInstance();
    private final IHelpController helpController = HelpController.getInstance();
    private final IFSStateDirector fsStateDirector = FSStateDirector.getInstance();

    public static MenuBarView getInstance() {
        if(myself == null)
            myself = new MenuBarView();

        return myself;
    }

    private final MenuBar menuBar;
    private final Menu fileMenu;
    private final Menu editMenu;
    private final Menu helpMenu;

    private MenuBarView () {
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
