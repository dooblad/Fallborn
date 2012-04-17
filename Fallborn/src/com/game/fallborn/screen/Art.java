package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static final Bitmap fallBorn = loadImage("res/fallBorn.png");
	public static final Bitmap[][] sheetTest = loadSpriteSheet("res/sheetTest.png", 5, 5);

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
	
	private static Bitmap[][] loadSpriteSheet(String URL, int tilesX, int tilesY) {
		try {
			BufferedImage image = ImageIO.read(new File(URL));
			int width = image.getWidth();
			int height = image.getHeight();
			int tileWidth = width / tilesX;
			int tileHeight = height / tilesY;
			Bitmap[][] result = new Bitmap[tilesX][tilesY];
			for(int x = 0; x < tilesX; x++) {
				for(int y = 0; y < tilesY; y++) {
					for(int xx = 0; x < tileWidth; x++) {
						for(int yy = 0; y < tileHeight; y++) {
							//4 for loops, you know what they're for
							//image.getRGB(x, y);
							
						}
					}
				}
			}
			return result;
		} catch(IOException e) {
			
		}
		return null;	
	}
}
