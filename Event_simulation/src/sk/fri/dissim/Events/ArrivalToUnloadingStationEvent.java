package sk.fri.dissim.Events;

import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Entities.TruckState;
import sk.fri.dissim.Simulation.FiredEventNames;
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
		if(core.getUnloadingStation() == null && core.getQueueB().isEmpty()) {
			truck.setState(TruckState.UNLOADING);
			core.addEvent(new TruckUnloadingEvent(truck, core, executionTime + core.getUnloadingTime(truck.getActualCapacity())));
			core.setUnloadingStation(truck);
		} else {
			truck.setState(TruckState.WAITING_FOR_UNLOADING);
			truck.setStartedWaitingForUnloading(executionTime);
			core.getQueueB().add(truck);
			core.getStatisticModul().incUnloadingQueueSize(executionTime);
		}
		core.fireTrucksUpdate();
	}

}
