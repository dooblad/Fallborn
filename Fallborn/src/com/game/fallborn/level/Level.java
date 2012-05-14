package com.game.fallborn.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.MainScreen;
import com.game.fallborn.things.Thing;

public class Level {
	public int[][] tiles;
	public int width, height;
	public int tileSize;
	public int xScroll, yScroll;

	public Level(String URL, int tileSize) {
		try {
			BufferedImage image = ImageIO.read(new File(URL));
			this.tileSize = tileSize;
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width][height];

			for (int x = 0; x < width; x++) {
				for (int y = 0; y < height; y++) {
					int pixel = image.getRGB(x, y);
					int red = (pixel >> 16) & 0xff;
					int green = (pixel >> 8) & 0xff;
					int blue = (pixel) & 0xff;
					pixel = blue + (green << 8) + (red << 16);
					switch (pixel) {
					case LevelColors.GRASS:
						tiles[x][y] = TileID.GRASS;
						break;
					case LevelColors.SHRUB:
						tiles[x][y] = TileID.SHRUB;
						break;
					case LevelColors.STONE:
						tiles[x][y] = TileID.STONE;
						break;
					default:
						continue;
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Level(String URL, int width, int height, int tileSize) {

	}

	public Level() {
		// eventually semi-random
	}

	public void render(MainScreen screen) {
		// eventually only render the visible area
		for (int x = 0; x < width; x++) {
			for (int y = 0; y < height; y++) {
				switch (tiles[x][y]) {
				case TileID.GRASS:
					screen.blit(Art.staticTiles[0][0], x * tileSize - xScroll, y * tileSize - yScroll);
					break;
				case TileID.SHRUB:
					screen.blit(Art.buildingTiles[0][0], x * tileSize - xScroll, y * tileSize - yScroll);
					break;
				case TileID.STONE:
					screen.blit(Art.buildingTiles[1][0], x * tileSize - xScroll, y * tileSize - yScroll);
				default:
					continue;
				}
			}
		}
	}

	public int getTileX(double positionX) {
		return (int) (positionX / tileSize);
	}

	public int getTileY(double positionY) {
		return (int) (positionY / tileSize);
	}

	public void collided(Thing t) {
		do {
			for (int i = 0; i < t.getCollisions().length; i++) {
				t.setCollisions(i, false);
			}

			for (int i = 1; i < t.getWidth() - 1; i++) { // TOP
				if (tiles[getTileX(t.getPositionX() + t.getXSpeed() + i)][getTileY(t.getPositionY() + t.getYSpeed() + (t.getHeight() / 1.25))] != TileID.GRASS) {
					t.setCollisions(Thing.TOP, true);
					t.setYSpeed(t.getYSpeed() + 1);
				}
			}

			for (int i = 1; i < t.getWidth() - 1; i++) { // BOTTOM
				if (tiles[getTileX(t.getPositionX() + t.getXSpeed() + i)][getTileY(t.getPositionY() + t.getYSpeed() + (t.getHeight() / 1.15))] != TileID.GRASS) {
					t.setCollisions(Thing.BOTTOM, true);
					t.setYSpeed(t.getYSpeed() - 1);
				}
			}

			for (int i = (int) ((t.getHeight() / 1.25) + 1); i < (t.getHeight() / 1.15) - 1; i++) { // LEFT
				if (tiles[getTileX(t.getPositionX() + t.getXSpeed())][getTileY(t.getPositionY() + t.getYSpeed() + i)] != TileID.GRASS) {
					t.setCollisions(Thing.LEFT, true);
					t.setXSpeed(t.getXSpeed() + 1);
				}
			}

			for (int i = (int) ((t.getHeight() / 1.25) + 1); i < (t.getHeight() / 1.15) - 1; i++) { // RIGHT
				if (tiles[getTileX(t.getPositionX() + t.getWidth() + t.getXSpeed())][getTileY(t.getPositionY() + t.getYSpeed() + i)] != TileID.GRASS) {
					t.setCollisions(Thing.RIGHT, true);
					t.setXSpeed(t.getXSpeed() - 1);
				}
			}
		} while (t.getCollisions()[Thing.TOP] || t.getCollisions()[Thing.BOTTOM] || t.getCollisions()[Thing.LEFT] || t.getCollisions()[Thing.RIGHT]);
	}
	
	public int getXScroll() {
		return xScroll;
	}
	public void setXScroll(int xScroll) {
		this.xScroll = xScroll;
	}
	
	public int getYScroll() {
		return yScroll;
	}
	public void setYScroll(int yScroll) {
		this.yScroll = yScroll;
	}
	
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
	
	public int getTileSize() {
		return tileSize;
	}
}
