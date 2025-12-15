package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import ch.supsi.fscli.backend.business.filesystem.FSCommands.ls.IFSLsCommandBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Singleton
public class LsCommand implements IFSCommand {

    private final IFSLsCommandBusiness business;
    private final FileSystem fileSystem;
    private List<String> args = new ArrayList<>();

    @Inject
    public LsCommand(IFSLsCommandBusiness business, FileSystem fileSystem) {
        this.business = business;
        this.fileSystem = fileSystem;
    }

    @Override
    public String getCommandName() {
        return "ls";
    }

    @Override
    public void setArgs(List<String> args) {
        this.args = args;
    }

    @Override
    public CommandResult execute() {
        boolean showInode = false;
        List<String> targets = new ArrayList<>();

        if (this.args != null) {
            for (String arg : this.args) {
                if (arg.equals("-i")) {
                    showInode = true;
                } else if (arg.startsWith("*")) {
                    targets.addAll(findAllEntries());
                } else {
                    targets.add(arg);
                }
            }
        }

        // Caso A: Nessun target specificato -> ls sulla cartella corrente (.)
        if (targets.isEmpty()) {
            CommandResult result = business.ls(null, showInode);
            if (result == null) return null;

            String content = result.getContent();
            if (isErrorMessage(content)) {
                return result;
            }
            // Altrimenti formattiamo
            return new CommandResult(formatHierarchy(".", content), false);
        }

        // Uno o più target (es. ls folder1 folder2)
        StringBuilder finalOutput = new StringBuilder();

        for (int i = 0; i < targets.size(); i++) {
            String path = targets.get(i);
            CommandResult result = business.ls(path, showInode);
            String content = (result != null) ? result.getContent() : "";

            if (isErrorMessage(content)) {
                finalOutput.append(content);
            } else {
                Inode node = fileSystem.getCurrentWorkingDirectory().getEntry(path);

                if (node != null && node.getType() == InodeType.FILE) {
                    finalOutput.append(content);
                } else {
                    finalOutput.append(formatHierarchy(path, content));
                }
            }

            if (i < targets.size() - 1) {
                finalOutput.append("\n");
            }
        }

        return new CommandResult(finalOutput.toString(), false);
    }

    private List<String> findAllEntries() {
        List<String> matches = new ArrayList<>();
        Set<String> currentEntries = fileSystem.getCurrentWorkingDirectory().getEntries().keySet();

        for (String name : currentEntries) {
            if (!name.equals(".") && !name.equals("..")) {
                matches.add(name);
            }
        }
        return matches;
    }

    private String formatHierarchy(String parentName, String content) {
        if (content.isEmpty()) {
            return parentName; // Cartella vuota, stampa solo il nome
        }

        StringBuilder sb = new StringBuilder();
        sb.append(parentName).append("\n");

        String indentedContent = content.lines()
                .map(line -> "!- " + line)
                .collect(Collectors.joining("\n"));

        sb.append(indentedContent);
        return sb.toString();
    }

    private boolean isErrorMessage(String content) {
        return content.startsWith("label.") || content.contains("cannot access");
    }
}