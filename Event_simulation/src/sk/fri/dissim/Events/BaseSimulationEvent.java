package sk.fri.dissim.Events;

import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.SimCore.BaseEvent;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public abstract class BaseSimulationEvent extends BaseEvent<ResourceTransportSimulation> {

	protected Truck truck;

	public BaseSimulationEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(core, executionTime);
		this.truck = truck;
	}

	@Override
	public abstract void execute();

}
