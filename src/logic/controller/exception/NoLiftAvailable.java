package logic.controller.exception;

public class NoLiftAvailable extends Exception {

	private static final long serialVersionUID = 1L;

	public NoLiftAvailable(String message) {
		super(message);
	}

	public NoLiftAvailable(Throwable cause) {
		super(cause);
	}

	public NoLiftAvailable(String message, Throwable cause) {
		super(" +++ " + message + " +++ ", cause);
	}
}