package logic.controller;

import java.util.List;

import logic.model.LiftMatchResult;

public abstract interface LiftMatchListener {
	
	public void onThreadEnd(List<LiftMatchResult> results);
	
	public void onThreadRunning();

}
