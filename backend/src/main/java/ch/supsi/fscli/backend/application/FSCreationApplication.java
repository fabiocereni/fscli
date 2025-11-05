package ch.supsi.fscli.backend.application;

import ch.supsi.fscli.backend.business.FSCreationBusiness;
import ch.supsi.fscli.backend.business.IFSCreationBusiness;

public class FSCreationApplication implements IFSCreationApplication {

    private final IFSCreationBusiness fsCreationBusiness = FSCreationBusiness.getInstance();

    private static FSCreationApplication myself;

    private FSCreationApplication() {}

    public static FSCreationApplication getInstance() {
        if(myself == null)
            myself = new FSCreationApplication();

        return myself;
    }

    @Override
    public void createFileSystem() {
        fsCreationBusiness.newfs();
    }
}
