package shiftman.server;
//import java.util.Comparator; 

/**
 * Creates Worker objects. It has fields for the name and whether the worker is assigned to a shift
 */
public class Worker {
	private String _firstName;
	private String _surname;
	private boolean _assigned;
	
	public Worker(String firstName, String surname) throws InvalidWorkerException {
		if(firstName==""||surname=="") {
			throw new InvalidWorkerException("Empty name");
		}
		_firstName = firstName;
		_surname = surname;
		_assigned=false;
		System.out.println("\t\t@Object Worker created for " + _firstName +" "+_surname);
	}
	
	/**
	 * returns the full name as a string
	 */
	public String getName() {
		return _firstName +" "+_surname;
	}
	
	/**
	 * returns the full name as a string with the surname first
	 */
	public String getSurnameFirst() {
		return _surname +", "+_firstName;
	}
	
	/**
	 * returns the surname
	 */
	public String getSurname() {
		return _surname;
	}
	
	/**
	 * makes the worker assigned
	 */
	public void assignShift() {
		_assigned = true;
	}
	
	/**
	 * returns true if the worker is assigned to a shift
	 */
	public boolean isAssigned() {
		return _assigned;
	}
}
