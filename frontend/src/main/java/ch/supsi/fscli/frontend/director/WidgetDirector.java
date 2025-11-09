package ch.supsi.fscli.frontend.director;


import ch.supsi.fscli.frontend.event.FilesystemCreatedEvent;
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
    }
}
