package ch.supsi.fscli.backend.DAO.persistence;

import com.google.inject.Singleton;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@Singleton
public class FSDataReaderDAO implements IFSDataReaderDAO {
    @Override
    public String readFromAFile(File file) throws IOException {
        return Files.readString(file.toPath());
    }
}
