package ch.supsi.fscli.backend.application.filesystem.creation;

import ch.supsi.fscli.backend.business.filesystem.creation.IFSCreationBusiness;
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
