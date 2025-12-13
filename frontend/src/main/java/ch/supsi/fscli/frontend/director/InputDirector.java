package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.InputEvent;
import com.google.inject.Singleton;

@Singleton
public class InputDirector extends AbstractDirector {
    public void manageInput() {
        firePropertyChange(new InputEvent(this));
    }
}
