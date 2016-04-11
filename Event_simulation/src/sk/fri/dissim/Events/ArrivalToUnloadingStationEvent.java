package sk.fri.dissim.Events;

import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public class ArrivalToUnloadingStationEvent extends BaseSimulationEvent{

	public ArrivalToUnloadingStationEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(truck, core, executionTime);
	}

	@Override
	public void execute() {

	}

}
