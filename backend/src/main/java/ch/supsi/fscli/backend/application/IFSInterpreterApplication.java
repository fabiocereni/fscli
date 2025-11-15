package ch.supsi.fscli.backend.application;

public interface IFSInterpreterApplication {

    String getCurrentpath();
    String execute(String commandLine);

}
