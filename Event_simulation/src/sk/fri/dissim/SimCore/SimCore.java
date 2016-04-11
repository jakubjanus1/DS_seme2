package sk.fri.dissim.SimCore;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingWorker;

/**
 *
 * @author Jakub Janus
 * @param <T>
 */
public abstract class SimCore<T extends Cloneable> extends SwingWorker<T, T> {

	protected boolean isPaused;
	protected boolean terminateSimulation;
	protected boolean isFinished;
	protected int actualNumberOfReplication;
	protected int numberOfReplications;
	protected T statisticModul;
	protected int timeOfOneReplication;
	protected ArrayList<BaseEvent> eventQueue;
	protected double simulationTime;

	public SimCore(int numberOfReplications, int timeOfOneReplication, Class<T> statisticModulClass) throws InstantiationException, IllegalAccessException {
		this.statisticModul = statisticModulClass.newInstance();
		isPaused = false;
		terminateSimulation = false;
		isFinished = false;
		this.numberOfReplications = numberOfReplications;
		this.timeOfOneReplication = timeOfOneReplication;
		eventQueue = new ArrayList<>();
		simulationTime = 0;
	}

	public void runCore() {
		BaseEvent event;
		while(!eventQueue.isEmpty()) {
			event = eventQueue.remove(0);
			simulationTime = event.getExecutionTime();
			if(timeOfOneReplication >= simulationTime || timeOfOneReplication < 0) {
				event.execute();
			} else {
				eventQueue.clear();
			}
		}
	}

	public void addEvent(BaseEvent event) {
		if(eventQueue.isEmpty()) {
			eventQueue.add(event);
			return;
		}
		for(int i=0;i<eventQueue.size();i++) {
			if(eventQueue.get(i).getExecutionTime() > event.getExecutionTime()) {
				eventQueue.add(i, event);
				return;
			}
		}
		eventQueue.add(event);
	}

	public double getSimulationTime() {
		return simulationTime;
	}

	public int getTimeOfOneReplication() {
		return timeOfOneReplication;
	}

	public int getNumberOfReplications() {
		return numberOfReplications;
	}

	public boolean isFinished() {
		return isFinished;
	}

	public T getStatisticModul() {
		return statisticModul;
	}

	public int getActualNumberOfReplication() {
		return actualNumberOfReplication;
	}

	@Override
	protected T doInBackground() throws NoSuchMethodException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InterruptedException {
		for(actualNumberOfReplication = 1; actualNumberOfReplication <= numberOfReplications; actualNumberOfReplication++) {
			setNewReplication();
			runCore();
			publish((T)statisticModul.getClass().getMethod("clone").invoke(statisticModul));
			while(isPaused) {
				Thread.sleep(1000);
			}
			if(terminateSimulation) {
				break;
			}
		}
		isFinished = true;
		return statisticModul;
	}

	protected abstract void setNewReplication();

	@Override
	protected abstract void done();

	@Override
	protected abstract void process(List<T> chunks);
}