package sk.fri.dissim.Simulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.List;
import sk.fri.dissim.Events.TimeTickEvent;
import sk.fri.dissim.SimCore.SimCore;

/**
 *
 * @author Jakub Janus
 */
public abstract class ResourceTransportSimulation extends SimCore<Statistics>{

	private int sleepInterval;
	private final PropertyChangeSupport propertyChangeSupport;

	public ResourceTransportSimulation(int numberOfReplications, int timeOfOneReplication, PropertyChangeSupport propertyChangeSupport) throws InstantiationException, IllegalAccessException {
		super(numberOfReplications, timeOfOneReplication, Statistics.class);
		this.propertyChangeSupport = propertyChangeSupport;
		this.propertyChangeSupport.addPropertyChangeListener(FiredEventNames.SLEEP_SPEED_CHANGED, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				sleepInterval = (int)evt.getNewValue();
			}
		});
	}

	@Override
	protected void setNewReplication() {
		addEvent(new TimeTickEvent(this, 0));
	}

	public int getSleepInterval() {
		return sleepInterval;
	}

	public void firePropertyChange(String name, Object newValue){
		propertyChangeSupport.firePropertyChange(name, null, newValue);
	}

	@Override
	protected abstract void done();

	@Override
	protected abstract void process(List<Statistics> chunks);
}
