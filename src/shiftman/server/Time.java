package shiftman.server;

/**
 * This class creates the Time object. It has fields for the hour and minute. 
 * It contains methods for comparing times, and converting the time to a string.
 */
public class Time {
	private int _hour;
	private int _min;

	public Time(String time) throws InvalidTimeException {
		//time string format is hh:mm 
		_hour = Integer.parseInt(time.substring(0, 2));
		_min = Integer.parseInt(time.substring(3));
		
		if(_hour>=24||_hour<0||_min>=60||_min<0) {
			throw new InvalidTimeException("Invalid time");
		}
		System.out.println("\t\t@Object Time created for "+time);
	}
	
	/**
	 * returns Time as a string
	 * */
	public String toString() {
		return String.format("%02d:%02d",_hour,_min );
	}
	
	/**
	 * check if time is equal to another time
	 * */
	public boolean isEqualTo(Time time) {
		return (_hour==time._hour&&_min==time._min);
	}
	
	/**
	 * Check if time is before another time
	 *@return true if the time is before the given time
	 *@param time The time to compare to
	 * */
	public boolean isBefore(Time time) {
		
		if (_hour<time._hour) {
			return true;
		}else if (_hour>time._hour) {
			return false;
		}else {
			if(_min<time._min) {
				return true;
			}else {
				return false;
			}
		}
	}
	
	/**
	 * Convert the time to minutes
	 */
	public int asMinutes() {
		return _hour*60+_min;
	}

}
