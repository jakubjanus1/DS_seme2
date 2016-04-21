package sk.fri.dissim.Events;

import sk.fri.dissim.Entities.Truck;
import sk.fri.dissim.Entities.TruckState;
import sk.fri.dissim.Simulation.EntryData;
import sk.fri.dissim.Simulation.FiredEventNames;
import sk.fri.dissim.Simulation.ResourceTransportSimulation;

/**
 *
 * @author Jakub Janus
 */
public class ArrivalToPointCEvent extends BaseSimulationEvent {

	public ArrivalToPointCEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(truck, core, executionTime);
	}

	@Override
	public void execute() {
		Truck onRoadToLoading = core.findFirstInTrucks(TruckState.ON_ROAD_TO_LOAD);
		
		double timeOfArrival = executionTime + truck.travelingTime(EntryData.A_C);
		if(onRoadToLoading != null && timeOfArrival < onRoadToLoading.getOnRoadUntil()) {
			timeOfArrival = onRoadToLoading.getOnRoadUntil();
		}
		truck.setOnRoadUntil(timeOfArrival);
		truck.setState(TruckState.ON_ROAD_TO_LOAD);
		core.addEvent(new ArrivalToLoadingStationEvent(truck, core, timeOfArrival));
		core.fireTrucksUpdate();
	}
	
}
