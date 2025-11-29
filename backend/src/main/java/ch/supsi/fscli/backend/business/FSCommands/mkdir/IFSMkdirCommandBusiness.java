package ch.supsi.fscli.backend.business.FSCommands.mkdir;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSMkdirCommandBusiness.class)
public interface IFSMkdirCommandBusiness{
    Boolean mkdir(String name);
}
