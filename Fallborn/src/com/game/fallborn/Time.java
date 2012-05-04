package com.game.fallborn;

public class Time {
	private long time = 6500;
	private long dayLength = 10000;
	private long nightLength = (long)(dayLength * 2.0 / 3.0);
	private long transitionLength = (long) (nightLength * 1.0 / 10.0);
	
	public void tick() {
		if(time > dayLength) time = 0;
		else time++;
	}
	
	public long getTime() {
		return time;
	}
	public void setTime(long time) {
		this.time = time;
	}
	
	public long getDayLength() {
		return dayLength;
	}
	public void setDayLength(long dayLength) {
		this.dayLength = dayLength;
	}
	
	public long getNightLength() {
		return nightLength;
	}
	public void setNightLength(long nightLength) {
		this.nightLength = nightLength;
	}
	
	public long getTransitionLength() {
		return transitionLength;
	}
	public void setTransitionLength(long transitionLength) {
		this.transitionLength = transitionLength;
	}
	
	public boolean isNightTime() {
		if(time >= nightLength)
			return true;
		else
			return false;
	}
}
