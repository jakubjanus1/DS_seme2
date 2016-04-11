package sk.fri.dissim.Events;

import java.util.ArrayList;
import java.util.stream.Collectors;
import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Entities.TruckState;
import sk.fri.dissim.Simulation.EntryData;
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
		Truck onRoadToUnloading = core.getTrucks().stream().filter(t -> t.getState() == TruckState.ON_ROAD_TO_UNLOAD).findFirst().get();
		int capacityToLoad = core.getNeededResources() >= truck.getMaxCapacity() ? truck.getMaxCapacity() : core.getNeededResources();
		
		double timeOfArrival = executionTime + truck.travelingTime(EntryData.A_B);
		if(onRoadToUnloading != null && timeOfArrival < onRoadToUnloading.getOnRoadUntil()) {
			timeOfArrival = onRoadToUnloading.getOnRoadUntil();
		}
		truck.setOnRoadUntil(timeOfArrival);
		truck.setState(TruckState.ON_ROAD_TO_UNLOAD);
		core.addEvent(new ArrivalToUnloadingStationEvent(truck, core, timeOfArrival));
		
		if(!core.getQueueA().isEmpty()) {
			Truck nextToLoad = core.getQueueA().remove(0);
			nextToLoad.setState(TruckState.LOADING);
			nextToLoad.addWaitingForLoadingTime(executionTime);
			core.setLoadingStation(nextToLoad);
			core.getStatisticModul().decLoadingQueueSize(executionTime);
			core.addEvent(new TruckLoadingEvent(nextToLoad, core, executionTime));
		}
	}

}
