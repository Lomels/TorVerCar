package logic.controller.exception;

public class PassengerException extends Exception {

	private static final long serialVersionUID = 1L;

	public PassengerException(String message) {
		super(message);
	}

	public PassengerException(Throwable cause) {
		super(cause);
	}

	public PassengerException(String message, Throwable cause) {
		super(" +++ " + message + " +++ ", cause);
	}

}
