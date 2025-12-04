package view;

import ch.supsi.fscli.frontend.controller.IFSDataReaderController;

import java.io.File;

public class FileMenuController {

    private IFileChooserService fileChooserService;

    private IFSDataReaderController dataReaderController;

    public void setFileChooserService(IFileChooserService service) {
        this.fileChooserService = service;
    }

    public void onOpen() {
        File file = fileChooserService.showOpenDialog(null);
        if (file != null) {
            dataReaderController.reader(file);
        }
    }
}
