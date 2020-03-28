package logic.controller.exception;

public class ApiNotReachableException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiNotReachableException(String message) {
		super(message);
	}
	
	public ApiNotReachableException (Throwable cause) {
		super(cause);
	}

	public ApiNotReachableException (String message, Throwable cause) {
		super(" +++ " + message + " +++ ", cause);
	}
}