package shiftman.server;

/**
 * This class creates shift objects and contains methods related to adding workers to shifts
 * and checking if the shift overlaps another shift or is outside the working hours
 */
public class Shift {
	private Week _weekDay;
	private Time _startTime;
	private Time _endTime;
	private Staff _workers = new Staff();
	private Worker _manager;
	private int _minWorkers;
	private boolean _managed;
	
	public Shift(String weekDay, String start, String end, String minWorkers) throws InvalidWeekException, InvalidTimeException {
		//check if the weekDay is valid
		for(Week weekDays:Week.values()) {
			if(weekDay.equals(weekDays.toString())) {
				//set the day
				_weekDay=weekDays;
			}
		}
		if(_weekDay==null) {
				throw new InvalidWeekException("Invalid week day");
		}
			
		_startTime= new Time(start);
		_endTime = new Time(end);
		
		//check if the times are valid
		if (_endTime.isBefore(_startTime)||_endTime.equals(_startTime)) {
			throw new InvalidTimeException("End time is before start time");
		}
		
		_minWorkers = Integer.parseInt(minWorkers);
		_managed = false;
		System.out.println("\t\t@Object Shift created with " +_weekDay+" "+_startTime.toString()+"-"+_endTime.toString()+" for " +_minWorkers+" workers");
		
	}
	
	/**
	 * This method checks if the shift overlaps with another shift
	 */
	public boolean overlaps(Shift shift) {		
		if(_weekDay.toString().equals(shift._weekDay.toString())) {			
			if(_startTime.isEqualTo(shift._startTime)||_endTime.isEqualTo(shift._endTime)) {
				//true if start times or end times are equal
				return true;			
			}else if(_startTime.isBefore(shift._startTime)&&(shift._startTime.isEqualTo(_endTime)||shift._startTime.isBefore(_endTime))) {
				//true if shift starts before and ends after the start of the other shift
				return true;
			}else if(shift._startTime.isBefore(_startTime)&&(_startTime.isEqualTo(shift._endTime)||_startTime.isBefore(shift._endTime))) {
				//true if other shift starts before and ends after the start of this shift
				return true;
			}
			return false;
		}
		//return false if the days are different
		return false;
	}
	
	/**
	 * This method checks if the shift is within the working hours
	 */
	public boolean withinTime(WorkingDay hours) {
		if((_startTime.isEqualTo(hours.getStart())||hours.getStart().isBefore(_startTime))&&(_endTime.isEqualTo(hours.getEnd())||_endTime.isBefore(hours.getEnd()))) {
			return true;
		}
		return false;
	}
	
	/**
	 * This method adds a worker or manager to the shift
	 */
	public void addWorker(Worker worker, boolean isManager) throws InvalidWorkerException {
		//add the staff member as a manager if isManager is true
		if(isManager) {
			if(!_managed) {
				_managed = true;
				_manager = worker;
			}else {
				throw new InvalidWorkerException("shift already managed");
			}
		}else {
			//add the worker as a worker if isManager is false
			_workers.add(worker);
		}
	}
	
	/**
	 * This method returns the day of the week as a string
	 */
	public String getDayString() {
		return _weekDay.toString();
	}
	
	/**
	 * This method returns the day of the week
	 */
	public Week getDay() {
		return _weekDay;
	}
	
	/**
	 * This method returns the start time
	 */
	public Time getStart() {
		return _startTime;
	}
	
	/**
	 * This method returns the start time as a string
	 */
	public String getStartString() {
		return _startTime.toString();
	}
	
	/**
	 * This method returns the end time as a string
	 */
	public String getEndString() {
		return _endTime.toString();
	}
	
	/**
	 * This method returns true if the shift is managed
	 */
	public boolean hasManager() {
		return _managed;
	}
	
	/**
	 * This method returns true if the shift has less than the minimum number of workers
	 */
	public boolean isUnderStaffed() {
		return _minWorkers>_workers.numWorkers();
	}
	
	/**
	 * This method returns true if the shift has more than the minimum number of workers
	 */
	public boolean isOverStaffed() {
		return _minWorkers<_workers.numWorkers();
	}
	
	/**
	 * This method returns the details of the shift as a string
	 */
	public String toString() {
		String string = _weekDay.toString()+"["+_startTime.toString()+"-"+_endTime.toString()+"]";
		if(_managed) {
			string = string +" Manager:"+ _manager.getSurnameFirst();
		}else {
			string = string +" [No manager assigned]";
		}
		if(_workers.numWorkers()==0) {
			string = string+" [No workers assigned]";
		}else {
			string = string+" ["+_workers.toString()+"]";
		}
		return string;
	}
	
	/**
	 * This method returns only the time and day of the shift as a string
	 */
	public String shortString() {
		String string = _weekDay.toString()+"["+_startTime.toString()+"-"+_endTime.toString()+"]";
		return string;
	}
	
	/**
	 * This method returns true if the worker is assigned to the shift
	 */
	public boolean isWorker(Worker worker) {
		return _workers.inRegister(worker);
	}
	
	/**
	 * This method returns true if the manager is assigned
	 */
	public boolean isManager(Worker manager) {
		if(_manager==null) {
			return false;
		}
		return _manager.equals(manager);
	}
}
