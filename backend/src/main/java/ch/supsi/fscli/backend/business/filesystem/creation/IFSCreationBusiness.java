package ch.supsi.fscli.backend.business.filesystem.creation;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSCreationBusiness.class)
public interface IFSCreationBusiness {
    void newfs();
}
