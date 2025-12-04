package view;

import com.google.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;

@Singleton
public class FileChooserService implements IFileChooserService {

    @Override
    public File showOpenDialog(Window owner) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Open Filesystem Backup");
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        String userHome = System.getProperty("user.home");
        chooser.setInitialDirectory(new File(userHome));

        return chooser.showOpenDialog(owner);
    }
}