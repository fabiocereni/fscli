package ch.supsi.fscli.backend.business;

import java.util.Optional;

public class PathSolver {

    private static final IFSStateBusiness ifsStateBusiness = FSStateBusiness.getInstance();

    public static Optional<INode> resolveAbsolutePath(String absolutePath, DirectoryBusiness root) {
        if (absolutePath == null || absolutePath.isEmpty())
            return Optional.empty();

        if (absolutePath.equals("/"))
            return Optional.of(root);

        String[] tokens = absolutePath.split("/");
        INode current = root;

        for (String token : tokens) {
            if (token.isEmpty()) continue;


            if (current instanceof FileBusiness) {
                System.out.println("Percorso non valido: '" + current.getName() + "' è un file, non una directory");
                return Optional.empty();
            }

            DirectoryBusiness dir = (DirectoryBusiness) current;
            Optional<INode> next = dir.getContent().stream()
                    .filter(n -> n.getName().equals(token))
                    .findFirst();

            if (next.isEmpty()) {
                System.out.println("Percorso inesistente: " + token);
                return Optional.empty();
            }

            current = next.get();
        }

        return Optional.of(current);
    }


    public static Optional<INode> resolveRelativePath(String relativePath) {
        if (relativePath == null || relativePath.isEmpty())
            return Optional.of(ifsStateBusiness.getCurrentWorkingDirectory());

        String[] tokens = relativePath.split("/");
        INode current = ifsStateBusiness.getCurrentWorkingDirectory();

        for (String token : tokens) {
            if (token.isEmpty() || token.equals(".")) continue;

            if (token.equals("..")) {
                if (current.getParent() != null) {
                    current = current.getParent();
                } else {
                    System.out.println("Posizione attuale: /");
                    return Optional.empty();
                }
                continue;
            }



            if (current instanceof FileBusiness) {
                System.out.println("Percorso non valido: '" + current.getName() + "' è un file, non una directory");
                return Optional.empty();
            }

            DirectoryBusiness dir = (DirectoryBusiness) current;
            Optional<INode> next = dir.getContent().stream()
                    .filter(n -> n.getName().equals(token))
                    .findFirst();

            if (next.isEmpty()) {
                System.out.println("Percorso inesistente: " + token);
                return Optional.empty();
            }

            current = next.get();
        }

        return Optional.of(current);
    }

}
