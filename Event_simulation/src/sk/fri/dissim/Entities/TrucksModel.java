package sk.fri.dissim.Entities;

import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.table.AbstractTableModel;
import sk.fri.dissim.Simulation.ObjectFactory;

/**
 *
 * @author Jakub Janus
 */
public class TrucksModel extends AbstractTableModel implements Iterable<Truck> {

	private ArrayList<Truck> trucks;

	public TrucksModel(int variant) {
		trucks = ObjectFactory.createVariant(variant);
	}

	@Override
	public int getRowCount() {
		return trucks.size();
	}

	@Override
	public int getColumnCount() {
		return 5;
	}

	@Override
	public Object getValueAt(int rowIndex, int columnIndex) {
		switch (columnIndex) {
			case 0:
				return trucks.get(rowIndex).getId();
			case 1:
				return trucks.get(rowIndex).getActualCapacity();
			case 2:
				return trucks.get(rowIndex).getState().toString();
			case 3:
				return trucks.get(rowIndex).getWaitingForLoadingTime();
			case 4:
				return trucks.get(rowIndex).getWaitingForUnloadingTime();
			default:
				return "";
		}
	}

	@Override
	public String getColumnName(int col) {
		switch(col) {
			case 0:
				return "Id";
			case 1:
				return "Aktuálna kapacita";
			case 2:
				return "Stav";
			case 3:
				return "Celkový čas čakania na nakladanie";
			case 4:
				return "Celkový čas čakania na vykladanie";
			default:
				return "Chyba";
		}
	}

	@Override
	public Iterator<Truck> iterator() {
		return trucks.iterator();
	}

}
