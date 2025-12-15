package ch.supsi.fscli.frontend.view.menubar.buttonMenubar;

import ch.supsi.fscli.frontend.controller.persistence.IFSDataSaverController;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class SaveAsView implements IShow {
    @Inject
    private IFSDataSaverController dataSaverController;

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
