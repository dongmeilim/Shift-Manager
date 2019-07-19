package shiftman.server;

@SuppressWarnings("serial")
/**
 * An exception class for invalid times
 */
public class InvalidTimeException extends Exception {

	public InvalidTimeException(String msg){
		super(msg);
	}
}
