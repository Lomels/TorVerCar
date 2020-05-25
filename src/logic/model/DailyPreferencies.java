package logic.model;

public class DailyPreferencies {

	private Integer maxKilometer;
	private Integer maxTime;
	private Route startingRoute;

	public DailyPreferencies(Integer maxKilometer, Integer maxTime, Route startingRoute) {
		this.maxKilometer = maxKilometer;
		this.maxTime = maxTime;
		this.startingRoute = startingRoute;
	}
}
