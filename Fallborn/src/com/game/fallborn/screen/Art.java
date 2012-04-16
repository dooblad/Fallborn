package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static final Bitmap fallBorn = loadImage("res/fallBorn.png");

	private static Bitmap loadImage(String URL) {
		try {
			BufferedImage image = ImageIO.read(new File(URL));
			int width = image.getWidth();
			int height = image.getHeight();
			Bitmap result = new Bitmap(width, height);
			image.getRGB(0, 0, width, height, result.pixels, 0, width);
			return result;
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
