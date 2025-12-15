package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir.IFSRmdirCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Singleton
public class RmdirCommand implements IFSCommand {

    private final IFSRmdirCommandBusiness business;
    private final FileSystem fileSystem;

    private List<String> args = new ArrayList<>();

    @Inject
    public RmdirCommand(IFSRmdirCommandBusiness business, FileSystem fileSystem) {
        this.business = business;
        this.fileSystem = fileSystem;
    }

    @Override
    public String getCommandName() {
        return "rmdir";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        if (args.isEmpty()) return new CommandResult("label.wrongRmUse1", true);
        List<String> dirToDelete = new ArrayList<>();

        for (String dirName : args) {
            if (dirName.startsWith("*"))
                dirToDelete.addAll(findMatchingDirectories());
            else
                dirToDelete.add(dirName);
        }

        boolean errorOccurred = false;
        for (String directoryName : dirToDelete) {
            boolean success = business.rmdir(directoryName);
            if (!success) errorOccurred = true;
        }

        if (errorOccurred) return new CommandResult("label.wrongRmUse2", true);
        return null;
    }

    private List<String> findMatchingDirectories() {
        List<String> matches = new ArrayList<>();
        Set<String> currentDirectoryEntries = fileSystem.getCurrentWorkingDirectory().getEntries().keySet();

        for (String dirName : currentDirectoryEntries) {
            if (!dirName.equals(".") && !dirName.equals("..")) {
                matches.add(dirName);
            }
        }
        return matches;
    }
}
