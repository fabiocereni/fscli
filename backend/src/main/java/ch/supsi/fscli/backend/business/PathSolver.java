package ch.supsi.fscli.backend.business;

import java.util.Optional;

public class PathSolver {

    private static final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    public static Optional<INode> resolvePath(String path) {
        if (path == null || path.isEmpty())
            return Optional.empty();

        boolean isAbsolute = path.startsWith("/");
        String[] tokens = path.split("/");

        INode current = isAbsolute ? ifsStateBusiness.getRoot()
                : ifsStateBusiness.getCurrentWorkingDirectory();

        INode previousDirectory = current;

        for (String token : tokens) {

            if (token.isEmpty() || token.equals(".")) continue;

            if (token.equals("..")) {
                if (current.getParent() != null) {
                    current = current.getParent();
                } else {
                    current = ifsStateBusiness.getRoot();
                }
                previousDirectory = current;
                continue;
            }

            if (!(current instanceof DirectoryBusiness dir)) {
                return Optional.empty();
            }

            Optional<INode> next = dir.getContent().stream()
                    .filter(n -> n.getName().equals(token))
                    .findFirst();

            if (next.isEmpty()) {
                return Optional.empty();
            }

            previousDirectory = current;
            current = next.get();
        }

        if (current.getType() == NodeType.FILE) {
            return Optional.ofNullable(
                    previousDirectory.getType() == NodeType.DIRECTORY
                            ? previousDirectory
                            : current.getParent()
            );
        }

        return Optional.of(current);
    }
}
