package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.model.i18n.ISupportedLanguageModel;
import ch.supsi.fscli.frontend.model.i18n.SupportedLanguageModel;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;


public class MenuBarView {


    private static MenuBarView myself;

    private final IFSDataSaverController dataSaverController = FSDataSaverController.getInstance();
    private final QuitController quitController = QuitController.getInstance();
    private final IAboutView aboutViewController = AboutController.getInstance();
    private final IHelpController helpController = HelpController.getInstance();
    private final IPreferencesController preferencesController = PreferencesController.getInstance();

    private final ISupportedLanguageModel supportedLanguageModel = SupportedLanguageModel.getInstance();

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
        this.fileMenu = new Menu(supportedLanguageModel.getTranslation("label.file"));
        this.editMenu = new Menu(supportedLanguageModel.getTranslation("label.edit"));
        this.helpMenu = new Menu(supportedLanguageModel.getTranslation("label.help"));
        this.menuBar = new MenuBar();
    }


    public void initMenuBarView() {
        // FILE MENU
        MenuItem newMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.new"));
        newMenuItem.setId("newMenuItem");

        MenuItem openMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.open"));
        openMenuItem.setId("openMenuItem");

        MenuItem saveMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.save"));
        saveMenuItem.setId("saveMenuItem");
        saveMenuItem.setOnAction(actionEvent -> dataSaverController.save());
        saveMenuItem.setDisable(true);

        MenuItem saveAsMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.saveAs"));
        saveAsMenuItem.setId("saveAsMenuItem");
        saveAsMenuItem.setOnAction((actionEvent) -> dataSaverController.showSavingView());
        saveAsMenuItem.setDisable(true);

        MenuItem exitMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.exit"));
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
        MenuItem preferencesMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.preferences"));
        preferencesMenuItem.setId("preferencesMenuItem");
        preferencesMenuItem.setOnAction((actionEvent) -> preferencesController.showPreferencesView());

        this.editMenu.setId("editMenu");
        this.editMenu.getItems().add(preferencesMenuItem);

        // HELP MENU
        MenuItem helpMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.help"));
        helpMenuItem.setId("helpMenuItem");
        helpMenuItem.setOnAction(actionEvent -> helpController.showHelpView());

        MenuItem aboutMenuItem = new MenuItem(supportedLanguageModel.getTranslation("label.about"));
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
