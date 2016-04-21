package sk.fri.dissim.Entities;

import java.util.Random;

/**
 *
 * @author Jakub Janus
 */
public class Truck {

	private Random breakdownGenerator;
	private double breakdownProbability;
	private String id;
	private int maxCapacity;
	private int actualCapacity;
	private double onRoadUntil;
	private int repairTime;
	private int speed;
	private double startedWaitingForLoading;
	private double startedWaitingForUnloading;
	private double waitingForLoadingTime;
	private double waitingForUnloadingTime;
	private TruckState state;

	public Truck(Random seedGenerator, double breakdownProbability, int maxCapacity, int repairTime, int speed, String id) {
		breakdownGenerator = new Random(seedGenerator.nextLong());
		this.breakdownProbability = breakdownProbability;
		this.id = id;
		this.maxCapacity = maxCapacity;
		this.repairTime = repairTime;
		this.speed = speed;
		waitingForLoadingTime = 0.0;
		waitingForUnloadingTime = 0.0;
		actualCapacity = 0;
		state = TruckState.WAITING_FOR_LOADING;
	}

	public void reset() {
		waitingForLoadingTime = 0.0;
		waitingForUnloadingTime = 0.0;
		startedWaitingForLoading = 0.0;
		startedWaitingForUnloading = 0.0;
		onRoadUntil = 0.0;
		actualCapacity = 0;
		state = TruckState.WAITING_FOR_LOADING;
	}

	public double getOnRoadUntil() {
		return onRoadUntil;
	}

	public void setOnRoadUntil(double onRoadUntil) {
		this.onRoadUntil = onRoadUntil;
	}

	public double travelingTime(int distance) {
		return ((double)distance / speed) * 3600;
	}

	public int getMaxCapacity() {
		return maxCapacity;
	}

	public void setActualCapacity(int actualCapacity) {
		this.actualCapacity = actualCapacity;
	}

	public int getActualCapacity() {
		return actualCapacity;
	}

	public String getId() {
		return id;
	}

	public int getRepairTime() {
		return breakdownGenerator.nextDouble() < breakdownProbability ? repairTime : 0;
	}

	public void setStartedWaitingForLoading(double startedWaitingForLoading) {
		this.startedWaitingForLoading = startedWaitingForLoading;
	}

	public void setStartedWaitingForUnloading(double startedWaitingForUnloading) {
		this.startedWaitingForUnloading = startedWaitingForUnloading;
	}

	public double getWaitingForLoadingTime() {
		return waitingForLoadingTime;
	}

	public double addWaitingForLoadingTime(double time) {
		double result = time - startedWaitingForLoading;
		this.waitingForLoadingTime += result;
		return result;
	}

	public double getWaitingForUnloadingTime() {
		return waitingForUnloadingTime;
	}

	public double addWaitingForUnloadingTime(double time) {
		double result = time - startedWaitingForUnloading;
		this.waitingForUnloadingTime += result;
		return result;
	}

	public TruckState getState() {
		return state;
	}

	public void setState(TruckState state) {
		this.state = state;
	}
}