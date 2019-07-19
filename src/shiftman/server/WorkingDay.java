package shiftman.server;

/**
 * Creates an object for the working hours of a day
 */
public class WorkingDay {
	private Week _day;
	private Time _startWork;
	private Time _endWork;
	
	public WorkingDay(String DayOfWeek, String start, String end) throws InvalidWeekException, InvalidTimeException {
		//check which day to create working hours for
		for(Week weekDays:Week.values()) {
			if(DayOfWeek.equals(weekDays.toString())) {
				_day=weekDays;
			}
		}
		//throw exception if the day of the week is invalid
		if(_day==null) {
			throw new InvalidWeekException("Invalid week day");
		}
			
		_startWork= new Time(start);
		_endWork = new Time(end);
		
		//throw exception if the times are invalid
		if (_endWork.isBefore(_startWork)||_endWork.equals(_startWork)) {
			throw new InvalidTimeException("End time is before start time");
		}
		System.out.println("\t\t@Object Working hours created for " +_day+" "+_startWork.toString()+"-"+_endWork.toString());
	}
	
	/**
	 * returns the start time
	 */
	public Time getStart() {
		return _startWork;
	}
	/**
	 * returns the end time
	 */
	public Time getEnd() {
		return _endWork;
	}
	/**
	 * returns a formatted string of the working hours
	 */
	public String toString() {
		String string = _day.toString()+" "+_startWork.toString()+"-"+_endWork.toString();		
		return string;
	}
}
