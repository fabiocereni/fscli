package ch.supsi.fscli.backend.business.filesystem.commandWrapper;

import java.util.List;

public interface IFSCommand {
    String getCommandName();
    void setArgs(List<String> args);
    CommandResult execute();
}
