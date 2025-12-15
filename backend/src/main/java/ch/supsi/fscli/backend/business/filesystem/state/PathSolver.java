package ch.supsi.fscli.backend.business.filesystem.state;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.FileSystem;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class PathSolver {

    private final FileSystem fileSystem;

    @Inject
    public PathSolver(FileSystem fileSystem) {
        this.fileSystem = fileSystem;
    }

    public Optional<Inode> resolvePath(String path) {
        if (path == null || path.isBlank())
            return Optional.empty();

        if (path.contains("//"))
            return Optional.empty();

        boolean isAbsolute = path.startsWith("/");

        if (path.equals("/"))
            return Optional.of(fileSystem.getRoot());

        List<String> tokens = new ArrayList<>();
        StringTokenizer tokenizer = new StringTokenizer(path, "/");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();
            if (token.isEmpty() || token.equals("."))
                continue;
            if (token.equals("..")) {
                if (!tokens.isEmpty()) {
                    tokens.remove(tokens.size() - 1);
                }
                continue;
            }
            tokens.add(token);
        }

        Inode current = isAbsolute
                ? fileSystem.getRoot()
                : fileSystem.getCurrentWorkingDirectory();

        for (String part : tokens) {
            if (!(current instanceof DirectoryInodeBusiness dir))
                return Optional.empty();
            Inode next = dir.getEntry(part);
            if (next == null)
                return Optional.empty();
            current = next;
        }
        return Optional.of(current);
    }

    public DirectoryInodeBusiness extractParentDirectory(String path) {
        if (path == null || path.isBlank())
            return null;

        if (path.contains("//"))
            return null;

        if (path.endsWith("/") && !path.equals("/"))
            path = path.substring(0, path.length() - 1);

        int lastSlash = path.lastIndexOf("/");
        if (lastSlash < 0)
            return fileSystem.getCurrentWorkingDirectory();
        if (lastSlash == 0)
            return fileSystem.getRoot();

        String parentPath = path.substring(0, lastSlash);
        Optional<Inode> parent = resolvePath(parentPath);

        return parent.isPresent() && parent.get().getType() == InodeType.DIRECTORY
                ? (DirectoryInodeBusiness) parent.get()
                : null;
    }

    public String extractFileName(String path) {
        int idx = path.lastIndexOf("/");
        return path.substring(idx + 1);
    }

    public boolean nameAlreadyExists(DirectoryInodeBusiness dir, String name) {
        return dir.getEntry(name) != null;
    }
}
