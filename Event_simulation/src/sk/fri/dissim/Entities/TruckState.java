package sk.fri.dissim.Entities;

/**
 *
 * @author Jakub Janus
 */
public enum TruckState {

	WAITING_FOR_LOADING("Čakam na naloženie"),
	ON_ROAD_TO_UNLOAD("Na ceste vyložiť"),
	LOADING("Nakladám");

	private String state;
	private TruckState(String state) {
		this.state = state;
	}

	public String toStrig() {
		return state;
	}
}