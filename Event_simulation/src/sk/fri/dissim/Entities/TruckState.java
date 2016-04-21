package sk.fri.dissim.Entities;

/**
 *
 * @author Jakub Janus
 */
public enum TruckState {

	WAITING_FOR_LOADING("Čakam na naloženie"),
	WAITING_FOR_UNLOADING("Čakam na vyloženie"),
	ON_ROAD_TO_UNLOAD("Na ceste vyložiť"),
	ON_ROAD_TO_LOAD("Na ceste naložiť"),
	ON_ROAD_TO_POINT_C("Na ceste do bodu C"),
	ON_ROAD_TO_POINT_C_WITH_BREAKDOWN("Na ceste do bodu C s poruchou"),
	IDLE("Neaktívny"),
	UNLOADING("Vykladám"),
	LOADING("Nakladám");

	private String state;
	private TruckState(String state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return state;
	}
}