package ch.supsi.fscli.backend.business.filesystem.FSCommands.ls;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSLsCommandBusiness.class)
public interface IFSLsCommandBusiness {
    String ls(String object, boolean id);
}
