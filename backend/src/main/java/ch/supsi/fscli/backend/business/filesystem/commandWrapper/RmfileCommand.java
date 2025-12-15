package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmfile.IFSRmfileCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

@Singleton
public class RmfileCommand implements IFSCommand {

    private final IFSRmfileCommandBusiness business;
    private final FileSystem fileSystem;

    private List<String> args = new ArrayList<>();

    @Inject
    public RmfileCommand(IFSRmfileCommandBusiness business, FileSystem fileSystem) {
        this.business = business;
        this.fileSystem = fileSystem;
    }

    @Override
    public String getCommandName() {
        return "rm";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (args.isEmpty()) return new CommandResult("label.wrongRmFileUse1", true);
        List<String> filesToDelete = new ArrayList<>();

        for (String fileName : args) {
            if (fileName.startsWith("*"))
                filesToDelete.addAll(findMatchingFiles(fileName));
            else
                filesToDelete.add(fileName);
        }

        boolean errorOccurred = false;
        for (String fileName : filesToDelete) {
            boolean success = business.rmfile(fileName);
            if (!success) errorOccurred = true;
        }
        if (errorOccurred) return new CommandResult("label.wrongRmFileUse2", true);

        return null;
    }

    private List<String> findMatchingFiles(String pattern) {
        List<String> matches = new ArrayList<>();
        Map<String, Inode> entries = fileSystem.getCurrentWorkingDirectory().getEntries();

        for (Map.Entry<String, Inode> entry : entries.entrySet()) {
            String name = entry.getKey();
            Inode node = entry.getValue();
            if (!name.equals(".") && !name.equals("..") && node.getType() == InodeType.FILE) {
                matches.add(name);
            }
        }
        return matches;
    }

}
