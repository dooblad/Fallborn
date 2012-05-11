package com.game.fallborn.things.magic;

public enum Spells {
	FIRE_BALL(0), ICE_BLAST(1), WATER_WAVE(2), AIR_STORM(3);
	
	Spells(int index) {
		this.index = index;
	}
	public int index;
	public static int numberOfSpells = 4;
}
