package ch.supsi.fscli.backend.business.filesystem.FSCommands.mkdir;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSMkdirCommandBusiness.class)
public interface IFSMkdirCommandBusiness{
    boolean mkdir(String name);
}
