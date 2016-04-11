package sk.fri.dissim.Simulation;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.List;
import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Events.ArrivalToLoadingStationEvent;
import sk.fri.dissim.Events.TimeTickEvent;
import sk.fri.dissim.SimCore.SimCore;

/**
 *
 * @author Jakub Janus
 */
public abstract class ResourceTransportSimulation extends SimCore<Statistics>{

	private int sleepInterval;
	private final PropertyChangeSupport propertyChangeSupport;

	// simulation entities
	private ArrayList<Truck> trucks;
	private ArrayList<Truck> queueA;
	private ArrayList<Truck> queueB;
	private final int NEEDED_RESOURCES = 5000;
	private final int LOADING_SPEED = 180;
	private final int UNLOADING_SPEED = 200;
	private Truck loadingStation;
	private Truck unloadingStation;
	private int neededResources;

	public ResourceTransportSimulation(int variant, int numberOfReplications, int timeOfOneReplication, PropertyChangeSupport propertyChangeSupport) throws InstantiationException, IllegalAccessException {
		super(numberOfReplications, timeOfOneReplication, Statistics.class);

		//Entities initialization
		trucks = ObjectFactory.createVariant(variant);

		this.propertyChangeSupport = propertyChangeSupport;
		this.propertyChangeSupport.addPropertyChangeListener(FiredEventNames.SLEEP_SPEED_CHANGED, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				sleepInterval = (int)evt.getNewValue();
			}
		});
		this.propertyChangeSupport.addPropertyChangeListener(FiredEventNames.PAUSE_SIMULATION, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				isPaused = true;
			}
		});
		this.propertyChangeSupport.addPropertyChangeListener(FiredEventNames.END_SIMULATION, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				terminateSimulation = true;
			}
		});
		this.propertyChangeSupport.addPropertyChangeListener(FiredEventNames.CONTINUE_SIMULATION, new PropertyChangeListener() {

			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				isPaused = false;
			}
		});
	}

	public double getLoadingTime(int capacity) {
		return (capacity / (double)LOADING_SPEED) / 3600;
	}

	public double getUnloadingTime(int capacity) {
		return (capacity / (double)UNLOADING_SPEED) / 3600;
	}

	public int getNeededResources() {
		return neededResources;
	}

	public void unloadResources(int capacity) {
		neededResources -= capacity;
	}

	public ArrayList<Truck> getQueueA() {
		return queueA;
	}

	public ArrayList<Truck> getQueueB() {
		return queueB;
	}

	public ArrayList<Truck> getTrucks() {
		return trucks;
	}

	public Truck getLoadingStation() {
		return loadingStation;
	}

	public void setLoadingStation(Truck loadingStation) {
		this.loadingStation = loadingStation;
	}

	public Truck getUnloadingStation() {
		return unloadingStation;
	}

	public void setUnloadingStation(Truck unloadingStation) {
		this.unloadingStation = unloadingStation;
	}

	@Override
	protected void setNewReplication() {
		neededResources = NEEDED_RESOURCES;
		queueA = new ArrayList<>();
		queueB = new ArrayList<>();
		loadingStation = null;
		unloadingStation = null;
		for(Truck t : trucks) {
			t.reset();
			addEvent(new ArrivalToLoadingStationEvent(t, this, 0.0));
		}
		addEvent(new TimeTickEvent(this, 0.0));
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
