package sk.fri.dissim.Events;

import java.util.ArrayList;
import java.util.stream.Collectors;
import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Entities.TruckState;
import sk.fri.dissim.Simulation.EntryData;
import sk.fri.dissim.Simulation.FiredEventNames;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public class TruckLoadingEvent extends BaseSimulationEvent {

	public TruckLoadingEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(truck, core, executionTime);
	}

	@Override
	public void execute() {
		core.setLoadingStation(null);
		Truck onRoadToUnloading = core.findFirstInTrucks(TruckState.ON_ROAD_TO_UNLOAD);
		
		double timeOfArrival = executionTime + truck.travelingTime(EntryData.A_B);
		if(onRoadToUnloading != null && timeOfArrival < onRoadToUnloading.getOnRoadUntil()) {
			timeOfArrival = onRoadToUnloading.getOnRoadUntil();
		}
		truck.setOnRoadUntil(timeOfArrival);
		truck.setState(TruckState.ON_ROAD_TO_UNLOAD);
		core.addEvent(new ArrivalToUnloadingStationEvent(truck, core, timeOfArrival));
		
		if(!core.getQueueA().isEmpty() && core.getNeededResources() > 0) {
			Truck nextToLoad = core.getQueueA().remove(0);
			int capacityToLoad = core.getNeededResources() >= nextToLoad.getMaxCapacity() ? nextToLoad.getMaxCapacity() : core.getNeededResources();
			core.unloadResources(capacityToLoad);
			nextToLoad.setState(TruckState.LOADING);
			core.getStatisticModul().incWaitingTimeForLoad(nextToLoad.addWaitingForLoadingTime(executionTime));
			nextToLoad.setActualCapacity(capacityToLoad);
			core.setLoadingStation(nextToLoad);
			core.getStatisticModul().decLoadingQueueSize(executionTime);
			core.addEvent(new TruckLoadingEvent(nextToLoad, core, executionTime + core.getLoadingTime(capacityToLoad)));
		}
		core.fireTrucksUpdate();
	}

}
