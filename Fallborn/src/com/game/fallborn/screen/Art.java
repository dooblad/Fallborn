package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Art {
	public static final Bitmap[][] fallBornSheet = loadSpriteSheet("res/fallBornSheet.png", 15, 3);
	public static final Bitmap[][] staticTiles = loadSpriteSheet("res/staticTiles.png", 5, 5);
	public static final Bitmap[][] buildingTiles = loadSpriteSheet("res/buildingTiles.png", 5, 5);
	public static final Bitmap fireBall = loadImage("res/fireBall.png");
	public static final Bitmap inventorySlot = loadImage("res/inventorySlot.png");
	public static final Bitmap inventorySelected = loadImage("res/inventorySelected.png");

	private static Bitmap loadImage(String URL) {
		try {
			BufferedImage image = ImageIO.read(new File(URL));
			int width = image.getWidth();
			int height = image.getHeight();
			Bitmap result = new Bitmap(width, height);
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					result.pixels[x + y * width] = image.getRGB(x, y);
				}
			}
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

			for (int x = 0; x < tilesX; x++) {
				for (int y = 0; y < tilesY; y++) {
					result[x][y] = new Bitmap(tileWidth, tileHeight);
					for (int yy = 0; yy < tileHeight; yy++) {
						for (int xx = 0; xx < tileWidth; xx++) {
							int xxx = xx + x * tileWidth;
							int yyy = yy + y * tileHeight;
							result[x][y].pixels[xx + yy * tileWidth] = image.getRGB(xxx, yyy);
						}
					}
				}
			}
			return result;
		} catch (IOException e) {

		}
		return null;
	}
}
