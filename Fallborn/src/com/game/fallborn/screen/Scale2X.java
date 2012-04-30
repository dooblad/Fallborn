package com.game.fallborn.screen;

public class Scale2X {
	public static Bitmap scaleUp(Bitmap bitmap) {
		//Y U NO WORK?
		Bitmap result = new Bitmap(bitmap.width * 2, bitmap.height * 2);
		for(int y = 0; y < bitmap.height; y++) {
			for(int x = 0; x < bitmap.width; x++) {
				int color = bitmap.pixels[x + y * bitmap.width];
				result.pixels[x + y * bitmap.width] = color;
				result.pixels[(x + 1) + y * bitmap.width] = color;
				result.pixels[x + ((y + 1) * bitmap.width)] = color;
				result.pixels[x + ((y + 1) * bitmap.width)] = color;
			}
		}
		return result;
	}
}
