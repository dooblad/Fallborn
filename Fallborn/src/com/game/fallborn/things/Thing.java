package com.game.fallborn.things;

import com.game.fallborn.screen.Bitmap;

public class Thing {
	public double positionX, positionY;
	public double xSpeed, ySpeed;
	public double walkSpeed = 1.0;
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
	public Direction direction;
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	
	public double getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	
	public double getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}
	
	public double getXSpeed() {
		return xSpeed;
	}
	public void setXSpeed(double xSpeed) {
		this.xSpeed = xSpeed;
	}
	
	public double getYSpeed() {
		return ySpeed;
	}
	public void setYSpeed(double ySpeed) {
		this.ySpeed = ySpeed;
	}
	
	public boolean[] getCollisions() {
		return collisions;
	}
	public void setCollisions(int index, boolean value) {
		collisions[index] = value;
	}
}
