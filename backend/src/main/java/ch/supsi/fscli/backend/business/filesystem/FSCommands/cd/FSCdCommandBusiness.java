package ch.supsi.fscli.backend.business.filesystem.FSCommands.cd;

import ch.supsi.fscli.backend.business.filesystem.structure.DirectoryInodeBusiness;
import ch.supsi.fscli.backend.business.filesystem.state.IFSStateBusiness;
import ch.supsi.fscli.backend.business.filesystem.structure.Inode;
import ch.supsi.fscli.backend.business.filesystem.structure.InodeType;
import ch.supsi.fscli.backend.business.filesystem.state.PathSolver;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

@Singleton
public class FSCdCommandBusiness implements IFSCdCommandBusiness {

    private final IFSStateBusiness stateBusiness;
    private final PathSolver pathSolver;

    @Inject
    public FSCdCommandBusiness(IFSStateBusiness stateBusiness, PathSolver pathSolver) {
        this.stateBusiness = stateBusiness;
        this.pathSolver = pathSolver;
    }

    @Override
    public boolean cd(String name) {
        if (name == null || name.isBlank()) return false;

        String currentPath = stateBusiness.getCurrentWorkingDirectoryPath();
        String pathToCheck;

        if (name.startsWith("/")) {
            pathToCheck = name;
        } else {
            if (currentPath.equals("/")) {
                pathToCheck = currentPath + name;
            } else {
                pathToCheck = currentPath + "/" + name;
            }
        }

        Optional<Inode> targetNodeOpt = pathSolver.resolvePath(pathToCheck);

        if (targetNodeOpt.isEmpty())
            return false;

        Inode targetNode = targetNodeOpt.get();
        if (targetNode.getType() != InodeType.DIRECTORY)
            return false;

        stateBusiness.setCurrentWorkingDirectory((DirectoryInodeBusiness) targetNode);

        String cleanPath = normalizePath(pathToCheck);
        stateBusiness.setCurrentWorkingDirectoryPath(cleanPath);

        return true;
    }

    /**
     * Metodo privato per pulire la stringa del percorso.
     * Serve per salvare nello stato "/home" invece di "/home/user/.."
     */
    private String normalizePath(String rawPath) {
        StringTokenizer tokenizer = new StringTokenizer(rawPath, "/");
        List<String> tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken().trim();

            if (token.isEmpty() || token.equals(".")) {
                continue;
            }

            if (token.equals("..")) {
                if (!tokens.isEmpty()) {
                    tokens.remove(tokens.size() - 1);
                }
                continue;
            }
            tokens.add(token);
        }

        if (tokens.isEmpty()) {
            return "/";
        }

        StringBuilder sb = new StringBuilder();
        for (String t : tokens) {
            sb.append("/").append(t);
        }
        return sb.toString();
    }
}