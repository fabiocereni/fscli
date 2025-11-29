package ch.supsi.fscli.backend.business;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    private Map<Long, Inode> inodeTable = new HashMap<>();
    private long nextInodeId = 1L;

    private DirectoryInodeBusiness root;

    private static FileSystem myself;

    public static FileSystem getInstance() {
        if (myself == null)
            myself = new FileSystem(true);

        return myself;
    }

    protected FileSystem() {}

    private FileSystem(boolean init) {
        // crea root con id allocato
        long id = allocateInodeId();
        this.root = new DirectoryInodeBusiness(id);
        this.root.incLinkCount(); // "/" è un link alla root

        inodeTable.put(id, root);
    }

    public DirectoryInodeBusiness createDirectory(DirectoryInodeBusiness parent) {
        long id = allocateInodeId();
        DirectoryInodeBusiness dir = new DirectoryInodeBusiness(id);
        inodeTable.put(id, dir);
        dir.incLinkCount(); // ogni directory deve avere almeno un link

        // TODO da sistemare
//        dir.addEntry(".", dir);
//        dir.addEntry("..", findParent(dir.getParentId()));
        return dir;
    }


    public FileInodeBusiness createFile() {
        long id = allocateInodeId();
        FileInodeBusiness file = new FileInodeBusiness(id);
        inodeTable.put(id, file);
        file.incLinkCount(); // ogni file ha almeno 1 link
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

    public void setInodeTable(Map<Long, Inode> inodeTable) {
        this.inodeTable = inodeTable;
    }

    public long getNextInodeId() {
        return nextInodeId;
    }

    public void setNextInodeId(long nextInodeId) {
        this.nextInodeId = nextInodeId;
    }

    public void setRoot(DirectoryInodeBusiness root) {
        this.root = root;
    }
}
