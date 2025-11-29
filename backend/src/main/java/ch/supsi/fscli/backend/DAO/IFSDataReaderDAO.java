package ch.supsi.fscli.backend.DAO;

import com.google.inject.ImplementedBy;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

@ImplementedBy(FSDataReaderDAO.class)
public interface IFSDataReaderDAO {
    String readFromAFile(File file) throws IOException;
    Properties getPreferences();
}
