package sk.fri.dissim.Events;

import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Entities.TruckState;
import sk.fri.dissim.Simulation.FiredEventNames;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public class ArrivalToLoadingStationEvent extends BaseSimulationEvent {

	public ArrivalToLoadingStationEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(truck, core, executionTime);
	}

	@Override
	public void execute() {
		if(core.getNeededResources() > 0) {
			if(core.getLoadingStation() == null && core.getQueueA().isEmpty()) {
				truck.setState(TruckState.LOADING);
				core.setLoadingStation(truck);
				int capacityToLoad = core.getNeededResources() >= truck.getMaxCapacity() ? truck.getMaxCapacity() : core.getNeededResources();
				core.unloadResources(capacityToLoad);
				truck.setActualCapacity(capacityToLoad);
				core.addEvent(new TruckLoadingEvent(truck, core, executionTime + core.getLoadingTime(capacityToLoad)));
			} else {
				truck.setState(TruckState.WAITING_FOR_LOADING);
				truck.setStartedWaitingForLoading(executionTime);
				core.getStatisticModul().incLoadingQueueSize(executionTime);
				core.getQueueA().add(truck);
			}
		} else {
			truck.setState(TruckState.IDLE);
			if(core.finished()) {
				core.getStatisticModul().finishReplication(executionTime);
			}
		}
		core.fireTrucksUpdate();
	}

}