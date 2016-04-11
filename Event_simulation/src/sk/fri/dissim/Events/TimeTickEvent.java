package sk.fri.dissim.Events;

import java.util.logging.Level;
import java.util.logging.Logger;
import sk.fri.dissim.SimCore.BaseEvent;
import sk.fri.dissim.Simulation.FiredEventNames;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public class TimeTickEvent extends BaseEvent<ResourceTransportSimulation>{

	public TimeTickEvent(ResourceTransportSimulation core, double executionTime) {
		super(core, executionTime);
	}

	@Override
	public void execute() {
		core.addEvent(new TimeTickEvent(core, executionTime + (501 - core.getSleepInterval())));
		try {
			Thread.sleep(core.getSleepInterval());
			core.firePropertyChange(FiredEventNames.TIME_TICK, executionTime);
		} catch (InterruptedException ex) {
			Logger.getLogger(TimeTickEvent.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}