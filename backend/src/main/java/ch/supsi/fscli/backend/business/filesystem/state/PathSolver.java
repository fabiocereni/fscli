package ch.supsi.fscli.backend.business.filesystem.state;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.*;

@Singleton
public class PathSolver {

    private final IFSStateBusiness ifsStateBusiness;

    @Inject
    public PathSolver(IFSStateBusiness ifsStateBusiness) {

        this.ifsStateBusiness = ifsStateBusiness;
    }

    public Optional<Inode> resolvePath(String path) {

        if (path == null || path.isBlank())
            return Optional.empty();

        if (path.contains("//"))
            return Optional.empty();

        boolean isAbsolute = path.startsWith("/");

        // caso root
        if (path.equals("/"))
            return Optional.of(ifsStateBusiness.getRoot());

        // 1) NORMALIZZAZIONE SOLO CON TOKENIZER
        List<String> tokens = new ArrayList<>();

        // se path è relativo → NON aggiungere nulla (CWD = implicit root)
        StringTokenizer tokenizer = new StringTokenizer(path, "/");

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty() || token.equals("."))
                continue;

            if (token.equals("..")) {
                // sali di un livello solo se puoi
                if (!tokens.isEmpty()) {
                    tokens.remove(tokens.size() - 1);
                }
                continue;
            }

            // token normale
            tokens.add(token);
        }

        // 2) NAVIGAZIONE SUGLI INODE
        Inode current = isAbsolute
                ? ifsStateBusiness.getRoot()
                : ifsStateBusiness.getCurrentWorkingDirectory();

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

        // togli eventuale "/" finale (escluso il root)
        if (path.endsWith("/") && !path.equals("/"))
            path = path.substring(0, path.length() - 1);

        int lastSlash = path.lastIndexOf("/");

        if (lastSlash < 0)
            return ifsStateBusiness.getCurrentWorkingDirectory();

        if (lastSlash == 0)
            return ifsStateBusiness.getRoot();

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
