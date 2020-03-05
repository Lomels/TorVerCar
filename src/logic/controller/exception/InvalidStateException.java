package logic.controller.exception;

public class InvalidStateException extends Exception{
	
		private static final long serialVersionUID = 1L;

		public InvalidStateException(String message) {
			super(message);
		}
		
		public InvalidStateException (Throwable cause) {
			super(cause);
		}

		public InvalidStateException (String message, Throwable cause) {
			super(" +++ " + message + " +++ ", cause);
		}
}

