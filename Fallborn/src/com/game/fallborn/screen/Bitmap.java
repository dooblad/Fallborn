package com.game.fallborn.screen;

import java.awt.Canvas;

public class Bitmap extends Canvas{
	public int width, height;
	public int[] pixels;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}
	
}
