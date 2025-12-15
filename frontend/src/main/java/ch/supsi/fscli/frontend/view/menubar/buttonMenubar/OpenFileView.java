package ch.supsi.fscli.frontend.view.menubar.buttonMenubar;

import ch.supsi.fscli.frontend.controller.persistence.IFSDataReaderController;
import ch.supsi.fscli.frontend.view.IShow;
import com.google.inject.Inject;
import jakarta.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.util.function.Supplier;

@Singleton
public class OpenFileView implements IShow {
    @Inject
    private IFSDataReaderController dataReaderController;

    private Supplier<FileChooser> chooserFactory = FileChooser::new;

    public void setChooserFactory(Supplier<FileChooser> factory) {
        this.chooserFactory = factory;
    }

    @Override
    public void showMyView() {
        FileChooser chooser = chooserFactory.get();
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
