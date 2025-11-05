package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.DAO.FSDataWriterDAO;
import ch.supsi.fscli.backend.DAO.IFSDataWriterDAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class FSDataWriterBusiness implements IFSDataWriterBusiness {

    private final IFSDataWriterDAO fsDataWriterDao = FSDataWriterDAO.getInstance();

    private static FSDataWriterBusiness myself;

    private FSDataWriterBusiness() {}

    public static FSDataWriterBusiness getInstance() {
        if(myself == null)
            myself = new FSDataWriterBusiness();

        return myself;
    }

    @Override
    public void save(Path path, AbstractFSBusiness abstractFSBusiness) {

        if(Files.exists(path)) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Error when deleting the file at: " + path);
            }

            this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(abstractFSBusiness));
        } else {
            this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(abstractFSBusiness));
        }

    }

    @Override
    public void save(AbstractFSBusiness abstractFSBusiness) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH");
        String fileName = LocalDateTime.now().format(formatter) + ".json";
        Path path = Paths.get(System.getProperty("user.home"), "FileSystem Simulator", "Saved", fileName);

        if(Files.exists(path)) {
            try {
                Files.deleteIfExists(path);
            } catch (IOException e) {
                System.err.println("Error when deleting the file at: " + path);
            }
            this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(abstractFSBusiness));
        } else {
            try {
                Files.createDirectories(path.getParent());
                this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(abstractFSBusiness));
            } catch (IOException e) {
                System.err.println("Default saving error");
            }

        }
    }
}