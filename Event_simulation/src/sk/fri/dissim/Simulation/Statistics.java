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
	private double sumFinishingTimeSim;
	private double sumXi;
	private double sumTrucksPerLoadingTimeSim;
	private double sumTrucksPerUnloadingTimeSim;
	private double sumWaitingTimeForLoadSim;
	private double sumWaitingTimeForUnloadSim;
	private int numberOfTrucks;
	private int numberOfLoadingsSim;
	private int numberOfUnloadingsSim;

	public Statistics() {
		sumTrucksPerLoadingTimeSim = 0.0;
		sumTrucksPerUnloadingTimeSim = 0.0;
		actualNumberOfReplications = 0;
		sumFinishingTimeSim = 0.0;
		sumWaitingTimeForLoadSim = 0.0;
		sumWaitingTimeForUnloadSim = 0.0;
		numberOfLoadingsSim = 0;
		numberOfUnloadingsSim = 0;
	}

	public Statistics(Statistics source) {
		sumTrucksPerLoadingTimeSim = source.sumTrucksPerLoadingTimeSim;
		sumTrucksPerUnloadingTimeSim = source.sumTrucksPerUnloadingTimeSim;
		actualNumberOfReplications = source.actualNumberOfReplications;
		sumFinishingTimeSim = source.sumFinishingTimeSim;
		sumWaitingTimeForLoadSim = source.sumWaitingTimeForLoadSim;
		sumWaitingTimeForUnloadSim = source.sumWaitingTimeForUnloadSim;
		numberOfTrucks = source.numberOfTrucks;
		sumXi = source.sumXi;
		numberOfLoadingsSim = source.numberOfLoadingsSim;
		numberOfUnloadingsSim = source.numberOfUnloadingsSim;
	}

	public void reset() {
		actualLoadingQueueSize = 0.0;
		lastChangeInLoadingQueue = 0.0;
		sumTrucksPerLoadingTime = 0.0;
		actualUnloadingQueueSize = 0.0;
		lastChangeInUnloadingQueue = 0.0;
		sumTrucksPerUnloadingTime = 0.0;
	}

	public void setNumberOfTrucks(int numberOfTrucks) {
		this.numberOfTrucks = numberOfTrucks;
	}

	public int getActualNumberOfReplications() {
		return actualNumberOfReplications;
	}

	public void incNumberOfLoadings() {
		numberOfLoadingsSim++;
	}

	public void incNumberOfUnloadings() {
		numberOfUnloadingsSim++;
	}

	public void finishReplication(double time) {
        sumXi += Math.pow(time/(double)3600, 2);
		sumFinishingTimeSim += time;
		actualNumberOfReplications++;
		sumTrucksPerLoadingTimeSim += sumTrucksPerLoadingTime/time;
		sumTrucksPerUnloadingTimeSim += sumTrucksPerUnloadingTime/time;
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
		sumTrucksPerUnloadingTime += actualUnloadingQueueSize * (time - lastChangeInUnloadingQueue);
		lastChangeInUnloadingQueue = time;
		actualUnloadingQueueSize++;
	}

	public void decUnloadingQueueSize(double time) {
		sumTrucksPerUnloadingTime += actualUnloadingQueueSize * (time - lastChangeInUnloadingQueue);
		lastChangeInUnloadingQueue = time;
		if(actualUnloadingQueueSize > 0) {
			actualUnloadingQueueSize--;
		}
	}

	public double getAverageWaitingForLoading() {
		return sumWaitingTimeForLoadSim / (double)numberOfTrucks / (double)actualNumberOfReplications / 3600.0;
	}

	public double getAverageWaitingForUnloading() {
		return sumWaitingTimeForUnloadSim / (double)numberOfTrucks / (double)actualNumberOfReplications / 3600.0;
	}

	public double getAverageWaitingForOneLoading() {
		return sumWaitingTimeForLoadSim / (double)numberOfLoadingsSim / 60.0;
	}

	public double getAverageWaitingForOneUnloading() {
		return sumWaitingTimeForUnloadSim / (double)numberOfUnloadingsSim / 60.0;
	}

	public double getAverageTrucksPerLoading() {
		return sumTrucksPerLoadingTimeSim / (double)actualNumberOfReplications;
	}

	public double getAverageTrucksPerUnloading() {
		return sumTrucksPerUnloadingTimeSim / (double)actualNumberOfReplications;
	}

	public double getAverageFinishingTime() {
		return sumFinishingTimeSim / (double)actualNumberOfReplications / (double)3600;
	}

    public double getConfidenceInterval(double tAlfa)
    {
        double a = ( (sumFinishingTimeSim/(double)actualNumberOfReplications/(double)3600) + tAlfa * ( getStandardDeviation() / Math.sqrt( actualNumberOfReplications - 1 )) );
        return a;
    }   
    
    public double getStandardDeviation()
    {
        double a = Math.sqrt( ( sumXi/(double)actualNumberOfReplications ) - Math.pow((sumFinishingTimeSim/(double)actualNumberOfReplications/(double)3600), 2) );
        return a;
    }

	@Override
	public Statistics clone() {
		return new Statistics(this);
	}
}
