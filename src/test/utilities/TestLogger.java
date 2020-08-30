package test.utilities;

import java.util.logging.Logger;

public class TestLogger {

	private Logger logger;
	private boolean isLogable;

	private static final String FORMAT = "%s is:\n%s\n";

//	public TestLogger(Object source) {
//		String className = source.getClass().getCanonicalName();
//		this.logger = Logger.getLogger(className);
//		this.isLogable = true;
//	}
	
	public TestLogger(String className) {
		this.logger = Logger.getLogger(className);
		this.isLogable = true;
	}

	public void isLogable(boolean isLogable) {
		this.isLogable = isLogable;
	}

	public void info(String name, Object object) {
		if (isLogable)
			logger.info(String.format(FORMAT, name, object.toString()));
	}

	public void info(String message) {
		if (isLogable)
			logger.info(message + "\n");
	}

	public void severe(String name, Object object) {
		if (isLogable)
			logger.severe(String.format(FORMAT, name, object.toString()));
	}

	public void severe(String message) {
		if (isLogable)
			logger.severe(message + "\n");
	}
}
