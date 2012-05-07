package com.game.fallborn;

public class Time {
	private long dayLength = 5000;
	private long nightLength = (long)(dayLength * 1.0 / 3.0);
	private long time = dayLength - nightLength;
	private double dayToNightRatio = ((double) dayLength) / nightLength;
	
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
	
	public double getDayToNightRatio() {
		return dayToNightRatio;
	}
	public void setDayToNightRatio(double dayToNightRatio) {
		this.dayToNightRatio = dayToNightRatio;
	}
	
	public boolean isNightTime() {
		if(time >= dayLength - nightLength)
			return true;
		else
			return false;
	}
}
