package ch.supsi.fscli.frontend.observable;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;

public abstract class Observable {

    protected PropertyChangeSupport pcs;

    public Observable() {
        this.pcs = new PropertyChangeSupport(this);
    }

    public List<PropertyChangeListener> getPropertyChangeListeners() {
        return List.of(this.pcs.getPropertyChangeListeners());
    }

    public void firePropertyChange(PropertyChangeEvent event) {
        this.pcs.firePropertyChange(event);
    }

    public void addPropertyChangeListener(PropertyChangeListener pcl) {
        pcs.addPropertyChangeListener(pcl);
    }

    public void removePropertyChangeListener(PropertyChangeListener pcl) {
        pcs.removePropertyChangeListener(pcl);
    }
}
