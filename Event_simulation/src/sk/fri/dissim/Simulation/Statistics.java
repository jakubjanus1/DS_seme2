package sk.fri.dissim.Simulation;

/**
 *
 * @author Jakub Janus
 */
public class Statistics implements Cloneable {

	//one replication
	private double actualLoadingQueueSize;
	private double lastChangeInLoadingQueue;
	private double sumTrucksPerLoadingTime;
	private double actualUnloadingQueueSize;
	private double lastChangeInUnloadingQueue;
	private double sumTrucksPerUnloadingTime;

	//simulation
	private int actualNumberOfReplications;
	private double sumTrucksPerLoadingTimeSim;
	private double sumTrucksPerUnloadingTimeSim;
	private double sumWaitingTimeForLoadSim;
	private double sumWaitingTimeForUnloadSim;

	public Statistics() {
		sumTrucksPerLoadingTimeSim = 0.0;
		sumTrucksPerUnloadingTimeSim = 0.0;
		actualNumberOfReplications = 0;
		sumWaitingTimeForLoadSim = 0.0;
		sumWaitingTimeForUnloadSim = 0.0;
	}

	public void reset() {
		actualLoadingQueueSize = 0.0;
		lastChangeInLoadingQueue = 0.0;
		sumTrucksPerLoadingTime = 0.0;
		actualUnloadingQueueSize = 0.0;
		lastChangeInUnloadingQueue = 0.0;
		sumTrucksPerUnloadingTime = 0.0;
	}

	public void setActualNumberOfReplications(int actualNumberOfReplications) {
		this.actualNumberOfReplications = actualNumberOfReplications;
	}

	public void incWaitingTimeForLoad(double waitingTime) {
		sumWaitingTimeForLoadSim += waitingTime;
	}

	public void incWaitingTimeForUnload(double waitingTime) {
		sumWaitingTimeForUnloadSim += waitingTime;
	}

	public void incLoadingQueueSize(double time) {
		sumTrucksPerLoadingTime += actualLoadingQueueSize * (time - lastChangeInLoadingQueue);
		lastChangeInLoadingQueue = time;
		actualLoadingQueueSize++;
	}

	public void decLoadingQueueSize(double time) {
		sumTrucksPerLoadingTime += actualLoadingQueueSize * (time - lastChangeInLoadingQueue);
		lastChangeInLoadingQueue = time;
		if(actualLoadingQueueSize > 0) {
			actualLoadingQueueSize--;
		}
	}

	public void incUnloadingQueueSize(double time) {
		sumTrucksPerLoadingTime += actualLoadingQueueSize * (time - lastChangeInLoadingQueue);
		lastChangeInLoadingQueue = time;
		actualLoadingQueueSize++;
	}

	public void decUnloadingQueueSize(double time) {
		sumTrucksPerUnloadingTime += actualUnloadingQueueSize * (time - lastChangeInUnloadingQueue);
		lastChangeInUnloadingQueue = time;
		if(actualUnloadingQueueSize > 0) {
			actualUnloadingQueueSize--;
		}
	}

	@Override
	public Statistics clone() {
		return null;
	}
}
