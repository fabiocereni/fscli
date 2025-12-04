package ch.supsi.fscli.backend.business.filesystem.structure;

import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Map;

@Singleton
public class FileSystem {

    private Map<Long, Inode> inodeTable = new HashMap<>();
    private long nextInodeId = 1L;

    private DirectoryInodeBusiness currentWorkingDirectory;
    private String currentWorkingDirectoryPath = "/";
    private DirectoryInodeBusiness root;

    @Inject
    public FileSystem(DirectoryInodeBusiness root) {
        this.root = root;
        this.inodeTable = new HashMap<>();
        initializeRoot(root);
    }

    public FileSystem() {
        this.inodeTable = new HashMap<>();
    }

    private void initializeRoot(DirectoryInodeBusiness root) {
        root.incLinkCount();   // "/" ha un link a se stessa
        inodeTable.put(root.getId(), root);
    }

    public DirectoryInodeBusiness createDirectory(DirectoryInodeBusiness parent) {
        long id = allocateInodeId();
        DirectoryInodeBusiness dir = new DirectoryInodeBusiness(id);
        inodeTable.put(id, dir);
        dir.incLinkCount();
        return dir;
    }

    public FileInodeBusiness createFile() {
        long id = allocateInodeId();
        FileInodeBusiness file = new FileInodeBusiness(id);
        inodeTable.put(id, file);
        file.incLinkCount();
        return file;
    }

    private long allocateInodeId() {
        return nextInodeId++;
    }

    public DirectoryInodeBusiness getRoot() {
        return root;
    }

    public Inode getInode(long id) {
        return inodeTable.get(id);
    }

    public Map<Long, Inode> getInodeTable() {
        return inodeTable;
    }

    public long getNextInodeId() {
        return nextInodeId;
    }

    public void setNextInodeId(long nextInodeId) {
        this.nextInodeId = nextInodeId;
    }

    public DirectoryInodeBusiness getCurrentWorkingDirectory() {
        return this.currentWorkingDirectory;
    }

    public void setCurrentWorkingDirectory(DirectoryInodeBusiness directory) {
        this.currentWorkingDirectory = directory;
    }

    public void setRoot(DirectoryInodeBusiness root) {
        this.root = root;
    }

    public String getCurrentWorkingDirectoryPath() {
        return currentWorkingDirectoryPath;
    }

    public void setCurrentWorkingDirectoryPath(String currentWorkingDirectoryPath) {
        this.currentWorkingDirectoryPath = currentWorkingDirectoryPath;
    }
}
