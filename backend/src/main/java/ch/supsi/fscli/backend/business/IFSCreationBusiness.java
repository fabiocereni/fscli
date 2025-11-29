package ch.supsi.fscli.backend.business;

import com.google.inject.ImplementedBy;

@ImplementedBy(FSCreationBusiness.class)
public interface IFSCreationBusiness {
    void newfs();
}
