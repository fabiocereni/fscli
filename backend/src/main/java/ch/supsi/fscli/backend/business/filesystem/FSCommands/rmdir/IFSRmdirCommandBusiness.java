package ch.supsi.fscli.backend.business.filesystem.FSCommands.rmdir;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSRmdirCommandBusiness.class)
public interface IFSRmdirCommandBusiness {
    boolean rmdir(String name);
}
