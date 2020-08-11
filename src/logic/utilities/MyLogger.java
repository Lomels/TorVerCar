package logic.utilities;

import java.util.logging.Logger;

public class MyLogger {

	public static void info(String name, Object object) {
		Logger.getGlobal().info(String.format("%s is: %s\n", name, object.toString()));
	}

	public static void info(String message) {
		Logger.getGlobal().info(message + "\n");
	}
	
	public static void severe(String name, Object object) {
		Logger.getGlobal().severe(String.format("%s is: %s\n", name, object.toString()));
	}
	
	public static void severe(String message) {
		Logger.getGlobal().severe(message + "\n");
	}
}
