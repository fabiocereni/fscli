package ch.supsi.fscli.frontend.model.filesystem;

public interface ICommandLineModel {
    String getCurrentPath();
    String executeCommand(String commandLine);
}
