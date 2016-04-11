package sk.fri.dissim.Simulation;

import java.util.ArrayList;
import java.util.Random;
import sk.fri.dissim.Entities.Truck;

/**
 *
 * @author Jakub Janus
 */
public class ObjectFactory {

	public static ArrayList<Truck> createVariant(int variant) {
		Random seedGenerator = new Random();
		ArrayList<Truck> result = new ArrayList<>();
		switch(variant) {
			case 1:
				result.add(createTruck1(seedGenerator));
				result.add(createTruck2(seedGenerator));
				result.add(createTruck3(seedGenerator));
				result.add(createTruck4(seedGenerator));
				break;
			case 2:
				result.add(createTruck1(seedGenerator));
				result.add(createTruck3(seedGenerator));
				result.add(createTruck5(seedGenerator));
				break;
			case 3:
				result.add(createTruck2(seedGenerator));
				result.add(createTruck3(seedGenerator));
				result.add(createTruck4(seedGenerator));
				break;
		}
		return result;
	}

	private static Truck createTruck1(Random seedGenerator) {
		return new Truck(seedGenerator, 0.12, 10, 4800, 60, "1");
	}

	private static Truck createTruck2(Random seedGenerator) {
		return new Truck(seedGenerator, 0.04, 20, 3000, 50, "2");
	}

	private static Truck createTruck3(Random seedGenerator) {
		return new Truck(seedGenerator, 0.04, 25, 6000, 45, "3");
	}

	private static Truck createTruck4(Random seedGenerator) {
		return new Truck(seedGenerator, 0.11, 5, 2640, 70, "4");
	}

	private static Truck createTruck5(Random seedGenerator) {
		return new Truck(seedGenerator, 0.06, 40, 10200, 30, "5");
	}
}