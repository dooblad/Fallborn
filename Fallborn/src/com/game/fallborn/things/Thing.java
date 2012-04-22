package com.game.fallborn.things;

import com.game.fallborn.screen.Bitmap;

public class Thing {
	public double positionX, positionY;
	public double xSpeed, ySpeed;
	public double walkSpeed = 1.0;
	public double sprintFactor = 2.0;
	public boolean facingRight = true;
	public int walkAnimationFactor = 4;
	public int walkTime = 0;
	public boolean[] collisions = new boolean[4];
	public static final int TOP = 0;
	public static final int BOTTOM = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public int width, height;
	public Bitmap[][] bitmap;
}
