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
public class TruckUnloadingEvent extends BaseSimulationEvent {

	public TruckUnloadingEvent(Truck truck, ResourceTransportSimulation core, double executionTime) {
		super(truck, core, executionTime);
	}

	@Override
	public void execute() {
		core.setUnloadingStation(null);
		double repairTime = truck.getRepairTime();
		if(repairTime > 0) {
			truck.setState(TruckState.ON_ROAD_TO_POINT_C_WITH_BREAKDOWN);
		} else {
			truck.setState(TruckState.ON_ROAD_TO_POINT_C);
		}
		truck.setActualCapacity(0);
		core.addEvent(new ArrivalToPointCEvent(truck, core, executionTime + truck.travelingTime(EntryData.B_C) + repairTime));
		
		if(!core.getQueueB().isEmpty()) {
			Truck nextToUnload = core.getQueueB().remove(0);
			core.setUnloadingStation(nextToUnload);
			nextToUnload.setState(TruckState.UNLOADING);
			core.addEvent(new TruckUnloadingEvent(nextToUnload, core, executionTime + core.getUnloadingTime(nextToUnload.getActualCapacity())));
			core.getStatisticModul().incWaitingTimeForUnload(nextToUnload.addWaitingForUnloadingTime(executionTime));
			core.getStatisticModul().decUnloadingQueueSize(executionTime);
		}
		core.fireTrucksUpdate();
	}
	
}
