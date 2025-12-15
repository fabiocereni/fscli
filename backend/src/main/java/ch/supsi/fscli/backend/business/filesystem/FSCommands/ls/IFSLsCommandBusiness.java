package ch.supsi.fscli.backend.business.filesystem.FSCommands.ls;

import ch.supsi.fscli.backend.business.filesystem.commandWrapper.CommandResult;
import com.google.inject.ImplementedBy;

@ImplementedBy(FSLsCommandBusiness.class)
public interface IFSLsCommandBusiness {
    CommandResult ls(String object, boolean id);
}
