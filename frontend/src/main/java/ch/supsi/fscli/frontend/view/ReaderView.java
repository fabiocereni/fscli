package ch.supsi.fscli.frontend.view;

import ch.supsi.fscli.frontend.controller.IFSDataReaderController;
import ch.supsi.fscli.frontend.controller.IFSDataSaverController;
import com.google.inject.Inject;
import jakarta.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

@Singleton
public class ReaderView implements IShow {

    @Inject
    private IFSDataReaderController dataReaderController;

    @Override
    public void showMyView() {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Filesystem Backup");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        String userHome = System.getProperty("user.home");
        chooser.setInitialDirectory(new File(userHome));

        File file = chooser.showOpenDialog(new Stage());

        if (file != null) {
            this.dataReaderController.reader(file);
        }
    }

}
