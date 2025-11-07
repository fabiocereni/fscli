package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSCreationBusiness;
import ch.supsi.fscli.backend.business.IFSCreationBusiness;
import com.google.inject.Singleton;

@Singleton
public class FSCreationApplication implements IFSCreationApplication {

    private final IFSCreationBusiness fsCreationBusiness = FSCreationBusiness.getInstance();


    @Override
    public void createFileSystem() {
        fsCreationBusiness.newfs();
    }
}
