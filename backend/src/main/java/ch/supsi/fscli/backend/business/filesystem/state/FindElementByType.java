package ch.supsi.fscli.backend.business.filesystem.state;

import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FindElementByType {

    public static List<String> findMatchingObjects(InodeType type, FileSystem fileSystem) {
        List<String> matches = new ArrayList<>();
        Map<String, Inode> entries = fileSystem.getCurrentWorkingDirectory().getEntries();

        for (Map.Entry<String, Inode> entry : entries.entrySet()) {
            String name = entry.getKey();
            Inode node = entry.getValue();
            if (!name.equals(".") && !name.equals("..") && node.getType() == type) {
                matches.add(name);
            }
        }
        return matches;
    }

}
