package ch.supsi.fscli.backend.business;

public class FSStateBusiness implements IFSStateBusiness {

    private boolean saved = false;

    private static FSStateBusiness myself;

    private FSStateBusiness() {}

    public static FSStateBusiness getInstance() {
        if(myself == null)
            myself = new FSStateBusiness();

        return myself;
    }

    @Override
    public boolean changeSavedStateAndGet() {
        saved = !saved;
        return saved;
    }
}
