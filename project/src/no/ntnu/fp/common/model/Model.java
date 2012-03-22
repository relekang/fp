package no.ntnu.fp.common.model;


import java.beans.PropertyChangeListener;

public interface Model {

    public boolean save();

    public void addPropertyChangeListener(PropertyChangeListener listener);
}
