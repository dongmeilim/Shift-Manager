package shiftman.server;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
/**
 * Roster has fields _shopName, _roster , and _workingHours.
 * It has methods to do with scheduling shifts, assigning workers to shifts,
 * and returning details of the roster. 
 */
public class Roster {
	private String _shopName;
	private ArrayList<Shift> _roster;
	private WorkingDay[] _workingHours;
	
	public Roster(String shopName) {
		_shopName = shopName;
		_roster = new ArrayList<Shift>();
		_workingHours = new WorkingDay[7];
		System.out.println("\t\t@Object Roster created for "+_shopName);
	}
	
	/**
	 * This method assigns the working hours for a given day. It will throw exceptions if the
	 * day or the times are invalid
	 * @param dayOfWeek The day to set working hours for
	 * @param startTime When the working hours begin
	 * @param endTime When the working hours end
	 */
	public void setWorkingHours(String dayOfWeek, String startTime, String endTime) 
			throws InvalidWeekException, InvalidTimeException {
		boolean validDay=false;
		//get the day to set working hours for
		int whichDay = 0;
		for(Week weekDays:Week.values()) {
			if(dayOfWeek.equals(weekDays.toString())) {
				//set working hours if the day is a valid day of the week
				validDay=true;
				_workingHours[whichDay] = new WorkingDay(dayOfWeek, startTime, endTime);
			}
			whichDay++;
		}
		if(!validDay) {
			//throw exception if none of the weekday values match dayOfWeek
			throw new InvalidWeekException("");
		}
	}
	
	/**
	 * This method adds a shift to the roster. It will throw exceptions if the
	 * day or the times are invalid, the shift overlaps with another, or if no 
	 * working hours have been declared for the day
	 * @param dayOfWeek The day of the shift
	 * @param startTime When the shift begins
	 * @param endTime When the shift ends
	 * @param minimumWorkers The number of workers
	 */
	public void addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) 
			throws InvalidWeekException, InvalidTimeException, InvalidShiftException, NoWorkingHoursException {
		//make object for the shift
		Shift shift = new Shift(dayOfWeek,startTime,endTime,minimumWorkers);
		
		//check if shift overlaps with any existing shifts
		for(Shift existingShift:_roster) {
			if(existingShift.overlaps(shift)) {
				//throw exception if overlap
				throw new InvalidShiftException("shift overlaps");
			}
		}
		
		//check if the shift is within the working hours
		int whichDay = 0;	//get the day of the shift		
		for(Week weekDays:Week.values()) {
			if(dayOfWeek.equals(weekDays.toString())) {
				break;
			}
			whichDay++;
		}
		//throw exception the hours have not been declared for that day
		if(_workingHours[whichDay]==null) {
			throw new NoWorkingHoursException("no working hours");
		}
		//add the shift if it is within working hours
		if(shift.withinTime(_workingHours[whichDay])) {
			_roster.add(shift);
		}else {
			//throw exception if the shift is outside of working hours
			throw new InvalidShiftException("shift outside of working hours");
		}
		
	}
	/**
	 * This method assigns a worker or manager to a shift. It will throw exceptions if the
	 * day or the times are invalid, or the worker is invalid.
	 */
	public void assignStaff(String dayOfWeek, String startTime, String endTime, Worker worker,
			boolean isManager) throws InvalidWorkerException, InvalidShiftException{
		
		boolean shiftExists = false;
		//check if the shift is in the roster
		for(Shift shift:_roster) {
			if (dayOfWeek.equals(shift.getDayString())&&startTime.equals(shift.getStartString())&&endTime.equals(shift.getEndString())) {				
				//add worker if shift exists
				shift.addWorker(worker, isManager);
				shiftExists = true;
			}
		}		
		if(!shiftExists) {
			//if the shift is not in the roster, throw exception
			throw new InvalidShiftException("no such shift");
		}		
	}
	/**
	 * This method returns a formatted string containing the roster details
	 */
	public String toString() {
		if(_roster.size()==0) {
			return "";
		}
		String string = "Roster for: "+ _shopName+"\n";
		for(Shift shift:_roster) {
			string = string+shift.toString()+"\n";
		}
		return string;
	}
	
	/**
	 * This method returns a sorted array list of strings containing the details
	 * of the shifts without managers
	 */
	public List<String> shiftsWithoutManagers() {
		List<String> string = new ArrayList<String>();
		ArrayList<Shift> noManagers = new ArrayList<Shift>();
		
		//create a list of shifts without managers
		for(Shift shift:_roster) {
			if (!shift.hasManager()) {
				noManagers.add(shift);
			}
		}
		//sort the list of shifts
		Collections.sort(noManagers, new ShiftComparator());
		
		//convert the shifts into a list of strings
		for(Shift shift:noManagers) {
			string.add(shift.toString());
		}
		return string;
	}
	
	/**
	 * This method returns a sorted array list of strings containing the details
	 * of understaffed shifts
	 */
	public List<String> understaffedShifts() {
		List<String> string = new ArrayList<String>();
		ArrayList<Shift> understaffed = new ArrayList<Shift>();
		
		//create a list of understaffed shifts
		for(Shift shift:_roster) {
			if (shift.isUnderStaffed()) {
				understaffed.add(shift);
			}
		}
		//sort the list of shifts
		Collections.sort(understaffed, new ShiftComparator());
		
		//convert the shifts into a list of strings
		for(Shift shift:understaffed) {
			string.add(shift.toString());
		}
		return string;
	}
	
	/**
	 * This method returns a sorted array list of strings containing the details
	 * of understaffed shifts
	 */
	public List<String> overstaffedShifts() {
		List<String> string = new ArrayList<String>();
		ArrayList<Shift> overstaffed = new ArrayList<Shift>();
		
		//create a list of overstaffed shifts
		for(Shift shift:_roster) {
			if (shift.isOverStaffed()) {
				overstaffed.add(shift);
			}
		}
		
		//sort the list of shifts
		Collections.sort(overstaffed, new ShiftComparator());
		
		//convert the shifts into a list of strings
		for(Shift shift:overstaffed) {
			string.add(shift.toString());
		}
		return string;
	}
	
	/**
	 * This method returns a formatted and sorted array list of strings containing the details
	 * of shifts for a certain day
	 */
	public List<String> getRosterForDay(String dayOfWeek) throws InvalidWeekException {
		List<String> rosterString = new ArrayList<String>();
		ArrayList<Shift> shiftsForDay = new ArrayList<Shift>();
		
		//the first element contains the shop name
		rosterString.add(_shopName);
		
		boolean validDay=false;
		int whichDay = 0;
		int numShifts = 0;
		
		//find out which day to get the roster for
		for(Week weekDays:Week.values()) {
			if(dayOfWeek.equals(weekDays.toString())) {
				validDay=true;
				
				//the seconds element contains the day to get the roster for and the workings hours
				if(_workingHours[whichDay]!=null) {
					rosterString.add(_workingHours[whichDay].toString());
				}else {
					return new ArrayList<String>();
				}
				
				//get a list of the shifts for the day
				for(Shift shift:_roster) {
					if(weekDays.toString().equals(shift.getDayString())) {
						numShifts++;
						shiftsForDay.add(shift);
					}
				}
			}
			whichDay++;
		}
		//throw exception if the day of the week is invalid
		if(!validDay) {
			throw new InvalidWeekException("invalid day of week");
		}
		//return an empty list if there are no shifts for that day
		if(numShifts==0) {
			return new ArrayList<String>();
		}else {
			//sort the shifts
			Collections.sort(shiftsForDay,new ShiftComparator());
			
			//the remaining elements of the string list are the shift details
			for(Shift shift:shiftsForDay) {
				rosterString.add(shift.toString());
			}
			return rosterString;
		}
	}
	
	/**
	 * This method returns a formatted and sorted array list of strings containing the details
	 * of shifts for a certain worker
	 */
	public List<String> getRosterForWorker(Worker worker) {
		List<String> rosterString = new ArrayList<String>();
		ArrayList<Shift> shiftsForWorker = new ArrayList<Shift>();
		
		//get a list of shifts for the worker
		for(Shift shift:_roster) {
			if(shift.isWorker(worker)) {
				shiftsForWorker.add(shift);
			}
		}
		//if the worker has no shifts, return an empty list
		if(shiftsForWorker.size()==0) {
			return new ArrayList<String>();
		}
		//sort the shifts
		Collections.sort(shiftsForWorker,new ShiftComparator());
		
		//convert shift list to string list
		rosterString.add(worker.getSurnameFirst());
		for(Shift shift:shiftsForWorker) {
			rosterString.add(shift.shortString());
		}		
		return rosterString;
	}
	
	/**
	 * This method returns a formatted and sorted array list of strings containing the details
	 * of shifts for a certain manager
	 */
	public List<String> getShiftsManagedBy(Worker manager) {
		List<String> rosterString = new ArrayList<String>();
		ArrayList<Shift> shiftsForManager = new ArrayList<Shift>();
		
		//get a list of shifts managed by this manager
		for(Shift shift:_roster) {
			if(shift.isManager(manager)) {
				shiftsForManager.add(shift);
			}
		}
		
		//if they are not managing any shifts, return an empty list
		if(shiftsForManager.size()==0) {
			return new ArrayList<String>();
		}
		//sort the shifts
		Collections.sort(shiftsForManager,new ShiftComparator());
		
		//the first element in the list should be the name of the manager
		rosterString.add(manager.getSurnameFirst());
		
		//the following elements are the details of the shifts
		for(Shift shift:shiftsForManager) {
			rosterString.add(shift.shortString());
		}		
		return rosterString;
	}
}
