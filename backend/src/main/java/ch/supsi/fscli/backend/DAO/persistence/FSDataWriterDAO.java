package ch.supsi.fscli.backend.DAO.persistence;

import com.google.inject.Singleton;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

@Singleton
public class FSDataWriterDAO implements IFSDataWriterDAO {
    @Override
    public void save(Path path, String toPersist) {
        try {
            Files.writeString(path, toPersist);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
