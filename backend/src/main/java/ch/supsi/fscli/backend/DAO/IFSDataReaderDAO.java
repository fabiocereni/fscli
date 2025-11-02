package ch.supsi.fscli.backend.DAO;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public interface IFSDataReaderDAO {
    String readFromAFile(File file) throws IOException;
    Properties getPreferences();
}