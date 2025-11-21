package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.event.SaveEvent;
import com.google.inject.Singleton;

@Singleton
public class SaveEventDirector extends AbstractDirector {

    public void manageSave() {
        System.out.println("SAVE EVENT FIRED");
        firePropertyChange(new SaveEvent(this, "save", false, true));
    }

    public void manageSaveAs() {
        System.out.println("SAVE AS EVENT FIRED");
        firePropertyChange(new SaveEvent(this, "save as", false, true));
    }

}
