package ch.supsi.fscli.backend.business.filesystem.interpreter;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import com.google.inject.ImplementedBy;

@ImplementedBy(FSInterpreter.class)
public interface IFSInterpreter {
    String getCurrentpath();
    CommandResult execute(String commandLine);
}
