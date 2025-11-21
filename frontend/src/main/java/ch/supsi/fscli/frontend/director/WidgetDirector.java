package ch.supsi.fscli.frontend.director;


import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
import ch.supsi.fscli.frontend.event.InputEvent;
import ch.supsi.fscli.frontend.event.SaveEvent;
import com.google.inject.Singleton;
import javafx.scene.control.MenuItem;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

@Singleton
public class WidgetDirector extends AbstractDirector implements PropertyChangeListener {

    private MenuItem saveMenuItem;
    private MenuItem saveAsMenuItem;



    public void setColleagues(MenuItem saveMenuItem, MenuItem saveAsMenuItem) {
        this.saveMenuItem = saveMenuItem;
        this.saveAsMenuItem = saveAsMenuItem;
    }


    @Override
    public void propertyChange(PropertyChangeEvent evt) {

        if(evt instanceof FilesystemCreatedEvent) {
            System.out.println("Filesystem created!");
            this.saveMenuItem.setDisable(false);
            this.saveAsMenuItem.setDisable(false);
        }

        if(evt instanceof SaveEvent) {
            System.out.println("Filesystem saved!");

            if(evt.getPropertyName().equalsIgnoreCase("save as"))
                this.saveAsMenuItem.setDisable(true);
            else
                this.saveMenuItem.setDisable(true);
        }


        if(evt instanceof InputEvent) {
            this.saveMenuItem.setDisable(false);
            this.saveAsMenuItem.setDisable(false);
        }
    }
}
