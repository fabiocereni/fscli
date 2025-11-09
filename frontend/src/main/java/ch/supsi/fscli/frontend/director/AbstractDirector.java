package ch.supsi.fscli.frontend.director;

import ch.supsi.fscli.frontend.observable.Observable;
import javafx.stage.Stage;


public abstract class AbstractDirector extends Observable {

    protected Stage parentStage;

    public AbstractDirector() {}

    public Stage getParentStage() {
        return parentStage;
    }

    public void setParentStage(Stage parentStage) {
        this.parentStage = parentStage;
    }
}
