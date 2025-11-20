package ch.supsi.fscli.backend.business;

import java.util.Optional;

public class PathSolver {

    private static final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    public static Optional<INode> resolvePath(String path) {

        if(path.matches(".*/{2,}.*"))
            return Optional.empty();

        if (path.isEmpty())
            return Optional.empty();

        boolean isAbsolute = path.startsWith("/");
        String[] tokens = path.split("/");

        INode current = isAbsolute ? ifsStateBusiness.getRoot()
                : ifsStateBusiness.getCurrentWorkingDirectory();



        for (String token : tokens) {

            if (token.isEmpty() || token.equals(".")) continue;

            if (token.equals("..")) {
                if (current.getParent() != null) {
                    current = current.getParent();
                } else {
                    current = ifsStateBusiness.getRoot();
                }
                continue;
            }

            if (!(current instanceof DirectoryBusiness dir)) {
                return Optional.empty();
            }

            System.out.println(current.getName());

            Optional<INode> next = dir.getContent().stream()
                    .filter(n -> n.getName().equals(token))
                    .findFirst();

            if (next.isEmpty()) {
                return Optional.empty();
            }

            current = next.get();
        }

        return Optional.of(current);
    }


    public static IDirectoryBusiness extractParentDirectory(String path) {

        if(path.matches(".*/{2,}.*"))
            return null;

        int lastIndex = path.lastIndexOf("/");

        if(lastIndex < 0)
            return ifsStateBusiness.getCurrentWorkingDirectory();

        String parentPath = path.substring(0, lastIndex);
        Optional<INode> node = PathSolver.resolvePath(parentPath);

        if(node.isPresent() && node.get().getType().equals(NodeType.DIRECTORY))
            return (IDirectoryBusiness) node.get();

        return null;
    }

    public static String extractFileName(String path) {
        int lastIndex = path.lastIndexOf("/");
        return path.substring(lastIndex + 1);
    }

    public static boolean nameAlreadyExists(IDirectoryBusiness dir, String fileToCheck) {
        return dir.getContent().stream().anyMatch(f -> f.getName().equals(fileToCheck));
    }

}
