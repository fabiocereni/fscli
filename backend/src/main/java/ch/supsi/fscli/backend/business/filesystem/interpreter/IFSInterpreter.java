package ch.supsi.fscli.backend.business.filesystem.interpreter;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSInterpreter.class)
public interface IFSInterpreter {
    String getCurrentpath();
    String execute(String commandLine);
}
