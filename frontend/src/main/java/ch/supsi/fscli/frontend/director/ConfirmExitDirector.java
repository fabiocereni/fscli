package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.ConfirmExitEvent;

public class ConfirmExitDirector extends AbstractDirector {
    public void manageExit() {
        firePropertyChange(new ConfirmExitEvent(this));
    }
}
