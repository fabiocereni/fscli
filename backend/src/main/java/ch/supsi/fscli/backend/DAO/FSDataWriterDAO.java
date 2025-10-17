package ch.supsi.fscli.backend.DAO;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FSDataWriterDAO implements IFSDataWriterDAO {

    private static FSDataWriterDAO myself;

    private FSDataWriterDAO(){}

    public static FSDataWriterDAO getInstance() {
        if (myself == null)
            myself = new FSDataWriterDAO();
        return myself;
    }

    @Override
    public void save(Path path, String toPersist) {
        try {
            Files.writeString(path, toPersist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}