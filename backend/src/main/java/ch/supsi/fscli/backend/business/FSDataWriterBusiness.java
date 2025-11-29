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

    private String lastName = "";


    private final IFSDataWriterDAO fsDataWriterDao = FSDataWriterDAO.getInstance();

    private static FSDataWriterBusiness myself;

    private FSDataWriterBusiness() {}

    public static FSDataWriterBusiness getInstance() {
        if (myself == null)
            myself = new FSDataWriterBusiness();

        return myself;
    }

    @Override
    public void save(Path path) {

        PersistedWrapper wrapperToSerialize = new PersistedWrapper();
        wrapperToSerialize.setFileSystem(FileSystem.getInstance());
        wrapperToSerialize.setStateBusiness(FSStateBusiness.getInstance());

        this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(wrapperToSerialize));


    }

    @Override
    public void save() {

        PersistedWrapper wrapperToSerialize = new PersistedWrapper();
        wrapperToSerialize.setFileSystem(FileSystem.getInstance());
        wrapperToSerialize.setStateBusiness(FSStateBusiness.getInstance());

        // Se è la prima volta che salvo, genero un nome
        if (lastName.isEmpty()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy_HH-mm-ss");
            lastName = LocalDateTime.now().format(formatter) + ".json";
        }

        Path path = Paths.get(System.getProperty("user.home"),"FileSystem Simulator","Saved",lastName);

        try {
            Files.createDirectories(path.getParent());
            this.fsDataWriterDao.save(path, JsonSerializerBusiness.serialize(wrapperToSerialize));
        } catch (IOException e) {
            System.err.println("Default saving error: " + e.getMessage());
        }
    }

}