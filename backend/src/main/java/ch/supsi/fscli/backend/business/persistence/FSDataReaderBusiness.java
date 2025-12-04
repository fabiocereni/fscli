package ch.supsi.fscli.backend.business.persistence;

import ch.supsi.fscli.backend.DAO.persistence.IFSDataReaderDAO;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import java.io.File;
import java.io.IOException;
import java.util.Map;

@Singleton
public class FSDataReaderBusiness implements IFSDataReaderBusiness {

    private final FileSystem fileSystem;
    private final IFSDataReaderDAO fsDataReaderDAO;

    @Inject
    public FSDataReaderBusiness(FileSystem fileSystem, IFSDataReaderDAO fsDataReaderDAO) {
        this.fileSystem = fileSystem;
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
            FileSystem fileSystem1 = loadedWrapper.getFileSystem();

            this.fileSystem.setNextInodeId(loadedFS.getNextInodeId());

            Map<Long, Inode> currentInodeTable = this.fileSystem.getInodeTable();
            currentInodeTable.clear();
            currentInodeTable.putAll(loadedFS.getInodeTable());

            this.fileSystem.setRoot(fileSystem1.getRoot());
            this.fileSystem.setCurrentWorkingDirectory(fileSystem1.getCurrentWorkingDirectory());
            this.fileSystem.setCurrentWorkingDirectoryPath(fileSystem1.getCurrentWorkingDirectoryPath());

            System.out.println("FILESYSTEM LOADED FROM FILE: " + file.getAbsolutePath());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
