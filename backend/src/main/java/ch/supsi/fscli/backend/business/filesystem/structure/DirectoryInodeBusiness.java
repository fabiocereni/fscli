package ch.supsi.fscli.backend.business.filesystem.structure;

import java.util.*;

public class DirectoryInodeBusiness extends Inode {
    private Map<String, Inode> entries = new HashMap<>();

    private DirectoryInodeBusiness() {
        super();
    }

    public DirectoryInodeBusiness(long id) {
        super(id, InodeType.DIRECTORY);
    }

    public Map<String, Inode> getEntries() {
        return entries;
    }

    public void setEntries(Map<String, Inode> entries) {
        this.entries = entries;
    }

    public void addEntry(String name, Inode inode) {
        entries.put(name, inode);
    }

    public void removeEntry(String name) {
        entries.remove(name);
    }

    public Inode getEntry(String name) {
        return entries.get(name);
    }

    /**
     * Sostituire nei comandi il metodo:
     * - fileSystem.getCurrentWorkingDirectory().getEntries().keySet() con
     * - fileSystem.getCurrentWorkingDirectory().listNames()
     * @return
     */
    public Set<String> listNames() {
        return entries.keySet();
    }
}