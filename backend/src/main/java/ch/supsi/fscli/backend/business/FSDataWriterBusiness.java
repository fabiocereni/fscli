package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.DAO.IFSDataWriterDAO;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Singleton
public class FSDataWriterBusiness implements IFSDataWriterBusiness {

    private String lastName = "";

    private final FileSystem fileSystem;
    private final FSStateBusiness fsStateBusiness;
    private final IFSDataWriterDAO fsDataWriterDao;

    @Inject
    public FSDataWriterBusiness(FileSystem fileSystem,
                                FSStateBusiness fsStateBusiness,
                                IFSDataWriterDAO fsDataWriterDao) {
        this.fileSystem = fileSystem;
        this.fsStateBusiness = fsStateBusiness;
        this.fsDataWriterDao = fsDataWriterDao;
    }

    @Override
    public void save(Path path) {
        PersistedWrapper wrapper = new PersistedWrapper();
        wrapper.setFileSystem(fileSystem);
        wrapper.setStateBusiness(fsStateBusiness);

        fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(wrapper));
    }

    @Override
    public void save() {
        PersistedWrapper wrapper = new PersistedWrapper();
        wrapper.setFileSystem(fileSystem);
        wrapper.setStateBusiness(fsStateBusiness);

        if (lastName.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
            lastName = LocalDateTime.now().format(formatter) + ".json";
        }

        Path path = Paths.get(
                System.getProperty("user.home"),
                "FileSystem Simulator",
                "Saved",
                lastName
        );

        try {
            Files.createDirectories(path.getParent());
            fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(wrapper));
        } catch (IOException e) {
            System.err.println("Default saving error: " + e.getMessage());
        }
    }
}
