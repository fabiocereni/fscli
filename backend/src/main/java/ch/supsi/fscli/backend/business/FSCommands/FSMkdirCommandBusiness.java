package ch.supsi.fscli.backend.business.FSCommands;

import ch.supsi.fscli.backend.business.DirectoryBusiness;
import ch.supsi.fscli.backend.business.FSStateBusiness;
import ch.supsi.fscli.backend.business.IFSStateBusiness;

public class FSMkdirCommandBusiness implements IFSMkdirCommandBusiness {

    private static FSMkdirCommandBusiness myself;

    private final IFSStateBusiness stateBusiness = FSStateBusiness.getInstance();

    private FSMkdirCommandBusiness() {}

    public static FSMkdirCommandBusiness getInstance() {
        if (myself == null)
            myself = new FSMkdirCommandBusiness();
        return myself;
    }

    @Override
    public Boolean mkdir(String name) {

        // 1. Validazione input
        if (name == null || name.isBlank())
            return false;

        // 2. Directory corrente
        DirectoryBusiness currentDir = stateBusiness.getCurrentWorkingDirectory();
        if (currentDir == null)
            return false;

        // 3. Controllo esistenza
        boolean exists = currentDir.getContent()
                .stream()
                .anyMatch(d -> d.getName().equals(name));

        if (exists) return false;

        // 4. Creazione directory
        DirectoryBusiness newDir = new DirectoryBusiness(currentDir, name);

        return true;
    }
}