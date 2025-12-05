package view;


import javafx.stage.Window;

import java.io.File;

public interface IFileChooserService {
    File showOpenDialog(Window owner);
}
