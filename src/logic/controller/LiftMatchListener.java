package logic.controller;

import java.util.List;

import logic.model.Lift;

public abstract interface LiftMatchListener {

	public void onThreadEnd(List<Lift> matchedLifts);
	
	public void onThreadRunning(List<Lift> matchedLifts);

}
