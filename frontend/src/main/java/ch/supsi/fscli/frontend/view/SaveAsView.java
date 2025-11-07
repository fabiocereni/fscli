package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.FSDataSaverController;
import ch.supsi.fscli.frontend.controller.IFSDataSaverController;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class SaveAsView implements IShow {

    private final IFSDataSaverController dataSaverController;

    @Inject
    public SaveAsView(IFSDataSaverController dataSaverController) {
        this.dataSaverController = dataSaverController;
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
