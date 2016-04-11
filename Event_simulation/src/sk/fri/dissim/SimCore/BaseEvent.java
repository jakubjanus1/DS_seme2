package sk.fri.dissim.SimCore;

/**
 *
 * @author Jakub Janus
 * @param <T>
 */
public abstract class BaseEvent<T extends SimCore> {

	protected double executionTime;
	protected T core;

	public BaseEvent(T core, double executionTime) {
		this.core = core;
		this.executionTime = executionTime;
	}

	public abstract void execute();

	public double getExecutionTime() {
		return executionTime;
	}
}