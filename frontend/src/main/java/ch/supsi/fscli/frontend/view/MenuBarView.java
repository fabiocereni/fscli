package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.*;
import ch.supsi.fscli.frontend.controller.i18n.ISupportedLanguageController;
import ch.supsi.fscli.frontend.controller.preference.IPreferencesController;
import ch.supsi.fscli.frontend.director.FSCreationDirector;
import ch.supsi.fscli.frontend.director.WidgetDirector;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;

@Singleton
public class MenuBarView {

    @Inject
    private IFSDataSaverController dataSaverController;
    @Inject
    private QuitController quitController;
    @Inject
    private IAboutView aboutViewController;
    @Inject
    private IHelpController helpController;
    @Inject
    private IPreferencesController preferencesController;

    @Inject
    private IFSCreationController fsCreationController;
    @Inject
    private FSCreationDirector fsCreationDirector;
    @Inject
    private WidgetDirector widgetDirector;

    @Inject
    private ISupportedLanguageController supportedLanguageController;

    private MenuBar menuBar;
    private Menu fileMenu;
    private Menu editMenu;
    private Menu helpMenu;

    @Inject
    public void init () {
        this.fileMenu = new Menu(supportedLanguageController.getTranslation("label.file"));
        this.editMenu = new Menu(supportedLanguageController.getTranslation("label.edit"));
        this.helpMenu = new Menu(supportedLanguageController.getTranslation("label.help"));
        this.menuBar = new MenuBar();
        this.fsCreationDirector.addPropertyChangeListener(widgetDirector);

    }

    public void initMenuBarView() {
        // FILE MENU
        MenuItem newMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.new"));        newMenuItem.setId("newMenuItem");
        newMenuItem.setOnAction(actionEvent -> fsCreationController.createFileSystem());

        MenuItem openMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.open"));        openMenuItem.setId("openMenuItem");

        MenuItem saveMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.save"));        saveMenuItem.setId("saveMenuItem");
        saveMenuItem.setOnAction(actionEvent -> dataSaverController.save());
        saveMenuItem.setDisable(true);

        MenuItem saveAsMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.saveAs"));        saveAsMenuItem.setId("saveAsMenuItem");
        saveAsMenuItem.setOnAction((actionEvent) -> dataSaverController.showSavingView());
        saveAsMenuItem.setDisable(true);

        MenuItem exitMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.exit"));        exitMenuItem.setId("exitMenuItem");
        exitMenuItem.setOnAction((actionEvent) -> quitController.manageQuit());

        this.widgetDirector.setColleagues(saveMenuItem, saveAsMenuItem);

        this.fileMenu.setId("fileMenu");
        this.fileMenu.getItems().add(newMenuItem);
        this.fileMenu.getItems().add(new SeparatorMenuItem());
        this.fileMenu.getItems().add(openMenuItem);
        this.fileMenu.getItems().add(saveMenuItem);
        this.fileMenu.getItems().add(saveAsMenuItem);
        this.fileMenu.getItems().add(new SeparatorMenuItem());
        this.fileMenu.getItems().add(exitMenuItem);

        // EDIT MENU
        MenuItem preferencesMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.preferences"));        preferencesMenuItem.setId("preferencesMenuItem");
        preferencesMenuItem.setOnAction(actionEvent -> preferencesController.showPreferencesView());

        this.editMenu.setId("editMenu");
        this.editMenu.getItems().add(preferencesMenuItem);

        // HELP MENU
        MenuItem helpMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.help"));        helpMenuItem.setId("helpMenuItem");
        helpMenuItem.setOnAction(actionEvent -> helpController.showHelpView());

        MenuItem aboutMenuItem = new MenuItem(supportedLanguageController.getTranslation("label.about"));        aboutMenuItem.setId("aboutMenuItem");
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