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

	//simulation
	private double sumTrucksPerLoadingTimeSim;

	public Statistics() {
		actualLoadingQueueSize = 0.0;
		lastChangeInLoadingQueue = 0.0;
		sumTrucksPerLoadingTime = 0.0;
		sumTrucksPerLoadingTimeSim = 0.0;
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

	@Override
	public Statistics clone() {
		return null;
	}
}
