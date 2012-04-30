package com.game.fallborn.screen;

import java.awt.Canvas;

public class Bitmap extends Canvas {
	public int width, height;
	public int[] pixels;

	public Bitmap(int width, int height) {
		this.width = width;
		this.height = height;
		pixels = new int[width * height];
	}

	public void blit(Bitmap bitmap, int xOffset, int yOffset) {
		for (int y = 0; y < bitmap.height; y++) {
			int yy = y + yOffset;
			if (yy < 0 || yy >= height)
				continue;

			for (int x = 0; x < bitmap.width; x++) {
				int xx = x + xOffset;
				if (xx < 0 || xx >= width)
					continue;

				int color = bitmap.pixels[x + y * (bitmap.width)];
				if (color < 0)
					pixels[xx + yy * width] = color;
			}
		}
	}

	public void blitReverse(Bitmap bitmap, int xOffset, int yOffset) {
		for (int y = 0; y < bitmap.height; y++) {
			int yy = y + yOffset;
			if (yy < 0 || yy >= height)
				continue;
			for (int x = 0; x < bitmap.width; x++) {
				int xx = bitmap.width - x + xOffset;
				if (xx < 0 || xx >= width)
					continue;

				int color = bitmap.pixels[x + y * (bitmap.width)];
				if (color < 0)
					pixels[xx + yy * width] = color;
			}
		}
	}
	
	public void clonePixels(int[] pixels) {
		for(int i = 0; i < pixels.length; i++) {
			this.pixels[i] = pixels[i];
		}
	}

	public void fill(int color) {
		for (int i = 0; i < pixels.length; i++) {
			pixels[i] = color;
		}
	}

}
