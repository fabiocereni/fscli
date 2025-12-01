package ch.supsi.fscli.backend.business;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void addEntry(String name, Inode inode) {
        entries.put(name, inode);
    }

    public void removeEntry(String name) {
        entries.remove(name);
    }

    public Inode getEntry(String name) {
        return entries.get(name);
    }

    public Set<String> listNames() {
        return entries.keySet();
    }

    public String getNameOf(Inode inode) {
        for (var entry : entries.entrySet()) {
            if (entry.getValue() == inode)
                return entry.getKey();
        }
        return null;
    }

    public void setEntries(Map<String, Inode> entries) {
        this.entries = entries;
    }
}