package ch.supsi.fscli.backend.business;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PathResolverTest {

    private DirectoryBusiness root;
    private DirectoryBusiness first;
    private DirectoryBusiness second;
    private DirectoryBusiness third;
    private FileBusiness file;

    private String absolutPath;
    private String relativePath;

    @BeforeEach
    void setup() {
        this.root = new DirectoryBusiness(null, "root");
        this.first = new DirectoryBusiness(this.root, "first");
        this.second = new DirectoryBusiness(this.root, "second");
        this.third = new DirectoryBusiness(this.first, "third");
        this.file = new FileBusiness(third, "file.txt");
    }


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



    public static Optional<INode> resolveRelativePath(String relativePath, DirectoryBusiness start) {
        if (relativePath == null || relativePath.isEmpty())
            return Optional.of(start);

        String[] tokens = relativePath.split("/");
        INode current = start;

        for (String token : tokens) {
            if (token.isEmpty() || token.equals(".")) continue;

            if (token.equals("..")) {
                if (current.getParent() != null) {
                    current = current.getParent();
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


    @Test
    void testAbsolutePathResolver() {
        Optional<INode> node = resolveAbsolutePath("/first/third", root);
        assertTrue(node.isPresent());
        assertEquals("third", node.get().getName());
    }

    @Test
    void testRelativePathResolver() {
        Optional<INode> node = resolveRelativePath("../second", first);
        assertTrue(node.isPresent());
        assertEquals("second", node.get().getName());
    }

    @Test
    void testFileResolution() {
        Optional<INode> node = resolveAbsolutePath("/first/third/file.txt", root);
        assertTrue(node.isPresent());
        assertEquals("file.txt", node.get().getName());
    }

    @Test
    void testInvalidPath() {
        Optional<INode> node = resolveAbsolutePath("/first/fourth", root);
        assertTrue(node.isEmpty());
    }



}
