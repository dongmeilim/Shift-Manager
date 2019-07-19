package shiftman.server;

@SuppressWarnings("serial")
/**
 * An exception class for when the shift is scheduled to a day
 *  with no working hours declared
 */
public class NoWorkingHoursException extends Exception {
	public NoWorkingHoursException(String msg){
		super(msg);
	}
}
