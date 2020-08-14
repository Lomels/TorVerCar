package logic.utilities;

import java.util.logging.Logger;

public class MyLogger {

	private Boolean logPermission;

	public MyLogger() {
		this.logPermission = true;
	}

	public MyLogger(Boolean logPermission) {
		this.logPermission = logPermission;
	}

	public Boolean getLogPermission() {
		return logPermission;
	}

	public void setLogPermission(Boolean logPermission) {
		this.logPermission = logPermission;
	}

	public void infoB(String name, Object object) {
		if (this.logPermission)
			Logger.getGlobal().info(String.format("%s is: %s\n", name, object.toString()));
	}

	public void infoB(String message) {
		if (this.logPermission)
			Logger.getGlobal().info(message + "\n");
	}

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
