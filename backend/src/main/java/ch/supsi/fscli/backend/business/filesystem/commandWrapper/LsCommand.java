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
        boolean wildcardUsed = false;

        List<String> targets = new ArrayList<>();

        if (args != null) {
            for (String arg : args) {
                if ("-i".equals(arg)) {
                    showInode = true;
                } else if (arg.startsWith("*")) {
                    wildcardUsed = true;
                    targets.addAll(findAllEntries());
                } else {
                    targets.add(arg);
                }
            }
        }

        if (targets.isEmpty()) {
            CommandResult result = business.ls(null, showInode);
            if (result == null) return null;

            String content = result.getContent();
            if (result.isTranslatable()) {
                return result;
            }

            return new CommandResult(content, false);
        }

        StringBuilder output = new StringBuilder();

        for (int i = 0; i < targets.size(); i++) {
            String path = targets.get(i);
            CommandResult result = business.ls(path, showInode);

            if (result == null) {
                continue;
            }

            String content = result.getContent();

            if (result.isTranslatable()) {
                return result;
            } else {
                Inode inode = fileSystem
                        .getCurrentWorkingDirectory()
                        .getEntry(path);

                if (inode != null && inode.getType() == InodeType.FILE) {
                    output.append(content);
                } else {
                    if (wildcardUsed || targets.size() > 1) {
                        output.append(formatHierarchy(path, content));
                    } else {
                        output.append(content);
                    }
                }
            }

            if (i < targets.size() - 1) {
                output.append("\n");
            }
        }

        return new CommandResult(output.toString(), false);
    }

    private List<String> findAllEntries() {
        Set<String> entries = fileSystem
                .getCurrentWorkingDirectory()
                .getEntries()
                .keySet();

        return entries.stream()
                .filter(e -> !e.equals(".") && !e.equals(".."))
                .collect(Collectors.toList());
    }

    private String formatHierarchy(String parent, String content) {
        if (content == null || content.isBlank()) {
            return parent;
        }

        String indented = content.lines()
                .map(line -> "-> " + line)
                .collect(Collectors.joining("\n"));

        return parent + "\n" + indented;
    }
}