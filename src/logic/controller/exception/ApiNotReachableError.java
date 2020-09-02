package logic.controller.exception;

public class ApiNotReachableError extends Error {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public ApiNotReachableError(String message) {
		super(message);
	}
	
	public ApiNotReachableError (Throwable cause) {
		super(cause);
	}

	public ApiNotReachableError (String message, Throwable cause) {
		super(" +++ " + message + " +++ ", cause);
	}
}