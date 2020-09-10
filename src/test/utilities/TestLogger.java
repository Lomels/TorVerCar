package test.utilities;

import java.util.logging.Logger;

public class TestLogger {

	private Logger logger;

	private static final String FORMAT = "%s is:\n%s\n";
	
	public TestLogger(String className) {
		this.logger = Logger.getLogger(className);
	}


	public void info(String name, Object object) {
		String format = String.format(FORMAT, name, object.toString()); 
		logger.info(format);
	}

	public void info(String message) {
		String logMessage = message + "\n";
		logger.info(logMessage);
	}

	public void severe(String name, Object object) {
		String format = String.format(FORMAT, name, object.toString()); 
		logger.severe(format);
	}

	public void severe(String message) {
		String logMessage = message + "\n";
		logger.severe(logMessage);
	}
}
