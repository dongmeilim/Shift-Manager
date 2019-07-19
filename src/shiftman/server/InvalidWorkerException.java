package shiftman.server;

@SuppressWarnings("serial")
/**
 * An exception class for invalid workers
 */
public class InvalidWorkerException extends Exception {

	public InvalidWorkerException(String msg){
		super(msg);
	}
}
