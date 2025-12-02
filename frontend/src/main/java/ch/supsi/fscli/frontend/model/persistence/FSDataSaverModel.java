package ch.supsi.fscli.frontend.model.persistence;


import ch.supsi.fscli.backend.application.persistence.IFSDataWriterApplication;
import com.google.inject.Inject;
import com.google.inject.Singleton;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class FSDataSaverModel implements IFSDataSaverModel {

    private final IFSDataWriterApplication ifsDataWriterApplication;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");

    private String pathName = LocalDateTime.now().format(formatter) + ".json";

    private String pathToPrint = String.valueOf(Paths.get(
            System.getProperty("user.home"),
                    "FileSystem Simulator",
                            "Saved",
                            pathName));

    @Inject
    public FSDataSaverModel(IFSDataWriterApplication ifsDataWriterApplication) {
        this.ifsDataWriterApplication = ifsDataWriterApplication;
    }

    @Override
    public void save(Path path) {
        pathToPrint = path.toAbsolutePath() + ".json";
        this.ifsDataWriterApplication.save(path);
    }

    @Override
    public void save() {
        this.ifsDataWriterApplication.save();
    }

    @Override
    public String getPathToPrint() {
        return pathToPrint;
    }
}