package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Creates Staff object with a list of workers. It has methods for adding workers,
 * checking if workers are in the list, and returning formatted details of the Staff.
 */
public class Staff {
	private ArrayList<Worker> _register;
	
	public Staff() {
		_register = new ArrayList<Worker>();
		System.out.println("\t\t@Object Staff created");
	}
	
	/**
	 * Add a worker
	 * @throws InvalidWorkerException 
	 */
	public void add(String firstName, String lastName) throws InvalidWorkerException {
		if(this.inRegister(firstName,lastName)) {
			throw new InvalidWorkerException("already in register");
		}else {
			_register.add(new Worker(firstName,lastName));
		}
		
	}
	/**
	 * Add a worker
	 * @throws InvalidWorkerException 
	 */
	public void add(Worker worker) throws InvalidWorkerException {
		if(this.inRegister(worker)) {
			throw new InvalidWorkerException("already in register");
		}else {
			_register.add(worker);
		}
		
	}
	
	/**
	 * returns true if a worker with the given name is in the register
	 */
	public boolean inRegister(String firstName, String surname) {
		for(Worker worker:_register) {
			if (worker.getName().toLowerCase().equals(firstName.toLowerCase()+" "+surname.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns true if a worker with the given name is in the register
	 */
	public boolean inRegister(String name) {
		for(Worker worker:_register) {
			if (worker.getName().toLowerCase().equals(name.toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns true if the worker is in the register
	 */
	public boolean inRegister(Worker worker) {
		for(Worker thisWorker:_register) {
			if (thisWorker.getName().toLowerCase().equals(worker.getName().toLowerCase())) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * returns the index of the worker in the list
	 */
	public int workerIndex(String firstName, String surname) {
		for(int i = 0;i<_register.size();i++) {
			if (_register.get(i).getName().toLowerCase().equals(firstName.toLowerCase()+" "+surname.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * returns the index of the worker in the list
	 */
	public int workerIndex(String name) {
		for(int i = 0;i<_register.size();i++) {
			if (_register.get(i).getName().toLowerCase().equals(name.toLowerCase())) {
				return i;
			}
		}
		return -1;
	}
	/**
	 * returns the worker at the given index
	 */
	public Worker getWorker(int index) {
		return _register.get(index);
	}
	
	/**
	 * gives a worker the Assigned status
	 */
	public void makeAssigned(String firstName, String surname) {
		_register.get(this.workerIndex(firstName, surname)).assignShift();
	}
	
	/**
	 * returns a sorted list of the unassigned staff as strings
	 */
	public List<String> getUnassignedStaff() {
		List<Worker> unassigned = new ArrayList<Worker>();
		List<String> string = new ArrayList<String>();
		
		//get a list of unassigned workers
		for(Worker worker:_register) {
			if(!worker.isAssigned()) {
				unassigned.add(worker);
			}
		}
		
		//sort the Worker list
		Collections.sort(unassigned,new WorkerComparator());
		
		//Convert worker list to string list
		for(Worker worker:unassigned) {
			string.add(worker.getName());
		}
		return string;
	}
	
	/**
	 * returns the number of workers
	 */
	public int numWorkers() {
		return _register.size();
	}
	
	/**
	 * returns a formatted string of Staff details
	 */
	public String toString() {
		String string = "";
		
		//create a list of workers sorted by family name
		List<Worker> workers = new ArrayList<Worker>();
		for(Worker worker:_register) {
			workers.add(worker);
		}
		//sort the list
		Collections.sort(workers,new WorkerComparator());
		
		//convert the sorted worker list into a string
		boolean firstIndex = true;		
		for(Worker worker:workers) {
			if(firstIndex) {
				//for the first worker, do not add the comma
				firstIndex=false;
				string = string+worker.getName();
			}else {
				//add a comma and the worker to the string
				string = string+", "+worker.getName();
			}
		}
		return string;
	}
	
	/**
	 * returns a formatted string list of the Staff details
	 */
	public List<String> toStringList(){
		List<Worker> workers = new ArrayList<Worker>();
		List<String> stringlist = new ArrayList<String>();
		
		//create a list of workers
		for(Worker worker:_register) {
			workers.add(worker);
			}
		
		//sort the list
		Collections.sort(workers,new WorkerComparator());
		
		//convert into string list
		for(Worker worker:workers) {
			stringlist.add(worker.getName());
		}
		return stringlist;
	}

	
}
