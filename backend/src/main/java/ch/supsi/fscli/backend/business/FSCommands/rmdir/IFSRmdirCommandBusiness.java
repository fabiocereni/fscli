package ch.supsi.fscli.backend.business.FSCommands.rmdir;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSRmdirCommandBusiness.class)
public interface IFSRmdirCommandBusiness {
    boolean rmdir(String name);
}
