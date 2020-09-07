package logic.utilities;

import java.util.logging.Logger;

public class MyLogger {

	private MyLogger() {
		// Do nothing
	}

	private static final Logger LOGGER = Logger.getLogger(MyLogger.class.getCanonicalName());
	private static final String FORMAT = "%s is: %s\n";

	public static void info(String name, Object object) {
		String logMessage = String.format(FORMAT, name, object.toString());
		LOGGER.info(logMessage);
	}

	public static void info(String message) {
		String logMessage = message + "\n";
		LOGGER.info(logMessage);
	}

	public static void severe(String name, Object object) {
		String logMessage = String.format(FORMAT, name, object.toString());
		LOGGER.severe(logMessage);
	}

	public static void severe(String message) {
		String logMessage = message + "\n";
		LOGGER.severe(logMessage);
	}
}
