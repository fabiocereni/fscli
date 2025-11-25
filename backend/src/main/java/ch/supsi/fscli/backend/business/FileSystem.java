package ch.supsi.fscli.backend.business;

import java.util.HashMap;
import java.util.Map;

public class FileSystem {
    private final Map<Long, Inode> inodeTable = new HashMap<>();
    private long nextInodeId = 1L;

    private final DirectoryInodeBusiness root;

    private static FileSystem myself;

    public static FileSystem getInstance() {
        if (myself == null)
            myself = new FileSystem();

        return myself;
    }

    private FileSystem() {
        // crea root con id allocato
        long id = allocateInodeId();
        this.root = new DirectoryInodeBusiness(id);
        this.root.incLinkCount(); // "/" è un link alla root

        inodeTable.put(id, root);
    }

    public DirectoryInodeBusiness createDirectory() {
        long id = allocateInodeId();
        DirectoryInodeBusiness dir = new DirectoryInodeBusiness(id);
        inodeTable.put(id, dir);
        dir.incLinkCount(); // ogni directory deve avere almeno un link
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
}
