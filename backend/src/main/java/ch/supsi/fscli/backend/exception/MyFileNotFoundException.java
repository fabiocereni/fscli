package ch.supsi.fscli.backend.exception;

public class MyFileNotFoundException extends Exception {
    public MyFileNotFoundException(String message) {
        super(message);
    }
}
