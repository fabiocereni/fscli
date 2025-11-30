package ch.supsi.fscli.backend.business;

import ch.supsi.fscli.backend.DAO.IFSDataReaderDAO;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Singleton
public class FSDataReaderBusiness implements IFSDataReaderBusiness {

    private final FileSystem fileSystem;
    private final FSStateBusiness fsStateBusiness;
    private final IFSDataReaderDAO fsDataReaderDAO;

    @Inject
    public FSDataReaderBusiness(FileSystem fileSystem, FSStateBusiness fsStateBusiness, IFSDataReaderDAO fsDataReaderDAO) {
        this.fileSystem = fileSystem;
        this.fsStateBusiness = fsStateBusiness;
        this.fsDataReaderDAO = fsDataReaderDAO;
    }

    @Override
    public void reader(File file) {
        if (file == null || !file.exists())
            return;

        try {
            String content = fsDataReaderDAO.readFromAFile(file);

            PersistedWrapper loadedWrapper = JsonDeserializeBusiness.deserialize(content, PersistedWrapper.class);
            if (loadedWrapper == null)
                return;

            FileSystem loadedFS = loadedWrapper.getFileSystem();
            FSStateBusiness loadedState = loadedWrapper.getStateBusiness();

            this.fileSystem.setNextInodeId(loadedFS.getNextInodeId());

            Map<Long, Inode> currentInodeTable = this.fileSystem.getInodeTable();
            currentInodeTable.clear();
            currentInodeTable.putAll(loadedFS.getInodeTable());

            this.fsStateBusiness.setRoot(loadedState.getRoot());
            this.fsStateBusiness.setCurrentWorkingDirectory(loadedState.getCurrentWorkingDirectory());
            this.fsStateBusiness.setCurrentWorkingDirectoryPath(loadedState.getCurrentWorkingDirectoryPath());

            this.fsStateBusiness.setSaved(true);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
