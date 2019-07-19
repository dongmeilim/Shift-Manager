package shiftman.server;

import java.util.Comparator;

/**
 * A comparator for sorting Workers by surname
 */
public class WorkerComparator implements Comparator<Worker>{

	@Override
	public int compare(Worker o1, Worker o2) {
		
		return (o1.getSurname().compareTo(o2.getSurname()));
	}

}
