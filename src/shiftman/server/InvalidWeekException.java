package shiftman.server;

@SuppressWarnings("serial")
/**
 * An exception class for invalid days of the week
 */
public class InvalidWeekException extends Exception {

	public InvalidWeekException(String msg){
		super(msg);
	}
}
