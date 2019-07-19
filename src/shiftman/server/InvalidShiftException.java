package shiftman.server;

@SuppressWarnings("serial")
/**
 * An exception class for invalid shifts
 */
public class InvalidShiftException extends Exception {
	public InvalidShiftException(String msg){
		super(msg);
	}
}
