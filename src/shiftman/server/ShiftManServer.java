package shiftman.server;

import java.util.ArrayList;
import java.util.List;

public class ShiftManServer implements ShiftMan{
	
	private Roster _roster;
	private Staff _register;
	
	@Override
	public String newRoster(String shopName) {
		_roster = new Roster(shopName);
		 _register = new Staff();
		return "";
	}

	@Override
	public String setWorkingHours(String dayOfWeek, String startTime, String endTime) {
		try {
			_roster.setWorkingHours(dayOfWeek, startTime, endTime);
			return "";
		//return strings containing errors if exceptions are thrown		
		}catch(InvalidWeekException e) {
			return "ERROR: Invalid week day";
		}catch(InvalidTimeException e) {
			return "ERROR: Invalid time";
		}catch(NullPointerException e) {
			return "ERROR: no roster has been created";
		}
		
	}

	@Override
	public String addShift(String dayOfWeek, String startTime, String endTime, String minimumWorkers) {
		try {
			_roster.addShift(dayOfWeek, startTime, endTime, minimumWorkers);
			
		} 
		//return strings containing errors if exceptions are thrown	
		catch (InvalidWeekException e) {
			return "ERROR: Invalid week day";
		} catch (InvalidTimeException e) {
			return "ERROR: invalid time";
		} catch(InvalidShiftException e) {
			return "ERROR: invalid shift";
		} catch(NoWorkingHoursException e) {
			return "ERROR: Working hours not declared";
		}
		return "";
		
	}

	@Override
	public String registerStaff(String givenname, String familyName) {
	
		try {
			_register.add(givenname,familyName);
		} 
		//return strings containing errors if exceptions are thrown	
		catch (InvalidWorkerException e) {
			return"ERROR: Empty name or existing worker";
		}
		
		return "";
	}

	@Override
	public String assignStaff(String dayOfWeek, String startTime, String endTime, String givenName, String familyName,
		boolean isManager) {
		
		try {
			if(_register.inRegister(givenName, familyName)) {
				_roster.assignStaff(dayOfWeek, startTime, endTime, _register.getWorker(_register.workerIndex(givenName, familyName)), isManager);
				_register.makeAssigned(givenName, familyName); 
			}else {
				return "ERROR: worker not registered";
			}
			return "";
		}
		//return strings containing errors if exceptions are thrown	
		catch (InvalidWorkerException e) {
			return"ERROR: the shift already has a manager";
		} catch (InvalidShiftException e) {
			return"ERROR: The shift is invalid or does not exist";
		}
	}

	@Override
	public List<String> getRegisteredStaff() {
		try{
			return _register.toStringList();
		} catch(NullPointerException e){
			List<String> error=new ArrayList<String>();
			error.add("ERROR: no roster has been created");
			return error;
		}
	}

	@Override
	public List<String> getUnassignedStaff() {
		return _register.getUnassignedStaff();

	}

	@Override
	public List<String> shiftsWithoutManagers() {
		return _roster.shiftsWithoutManagers();
	}

	@Override
	public List<String> understaffedShifts() {
		return _roster.understaffedShifts();
	}

	@Override
	public List<String> overstaffedShifts() {
		return _roster.overstaffedShifts();
	}

	@Override
	public List<String> getRosterForDay(String dayOfWeek) {
		
		try {
			return _roster.getRosterForDay(dayOfWeek);
		} 
		//return strings containing errors if exceptions are thrown	
		catch (InvalidWeekException e) {
			List<String> error = new ArrayList<String>();
			error.add("ERROR: Invalid day of week");
			return error;
		}
	}

	@Override
	public List<String> getRosterForWorker(String workerName) {
		if(_register.inRegister(workerName)) {
			return _roster.getRosterForWorker(_register.getWorker(_register.workerIndex(workerName)));
		}
		//return string list containing error if the worker is not in the register
		List<String> error = new ArrayList<String>();
		error.add("ERROR: Worker not registered");
		return error;
	}

	@Override
	public List<String> getShiftsManagedBy(String managerName) {
		if(_register.inRegister(managerName)) {
			return _roster.getShiftsManagedBy(_register.getWorker(_register.workerIndex(managerName)));
		}else {
			//return string list containing error if the worker is not in the register
			List<String> error = new ArrayList<String>();
			error.add("ERROR: Worker not registered");
			return error;
		}
	}

	@Override
	public String reportRosterIssues() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String displayRoster() {
		return _roster.toString();
	}

}
