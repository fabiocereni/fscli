package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.FSDataSaverController;
import ch.supsi.fscli.frontend.controller.IFSDataSaverController;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

public class SaveAsView implements IShow {


    private static SaveAsView myself;

    private final IFSDataSaverController dataSaverController = FSDataSaverController.getInstance();

    private SaveAsView() {}

    public static SaveAsView getInstance() {
        if (myself == null) {
            myself = new SaveAsView();
        }

        return myself;
    }

    @Override
    public void showMyView() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = chooser.showSaveDialog(new Stage());

        if(file != null) {
            Path path = file.toPath();
            this.dataSaverController.save(path);
        }
    }
}
