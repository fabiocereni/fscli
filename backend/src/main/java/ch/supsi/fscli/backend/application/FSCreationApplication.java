package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.IFSCreationBusiness;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class FSCreationApplication implements IFSCreationApplication {

    private final IFSCreationBusiness fsCreationBusiness;

    @Inject
    public FSCreationApplication(IFSCreationBusiness fsCreationBusiness) {
        this.fsCreationBusiness = fsCreationBusiness;
    }

    @Override
    public void createFileSystem() {
        fsCreationBusiness.newfs();
    }
}
