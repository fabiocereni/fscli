package ch.supsi.fscli.frontend.view;

import jakarta.inject.Singleton;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.nio.file.Path;

@Singleton
public class LoadView implements IShow {

    @Override
    public void showMyView() {
        FileChooser chooser = new FileChooser();
        chooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("JSON", "*.json"));

        File file = chooser.showOpenDialog(new Stage());

        if(file != null) {
            Path path = file.toPath();

        }
    }
}