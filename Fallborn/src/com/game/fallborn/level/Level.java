package com.game.fallborn.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Screen;
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

	public void render(Screen screen) {
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
			for (int i = 0; i < t.collisions.length; i++) {
				t.collisions[i] = false;
			}
			
			for (int i = 1; i < t.width - 1; i++) {
				if (tiles[getTileX(t.positionX + t.xSpeed + i)][getTileY(t.positionY + t.ySpeed + (t.height / 1.25))] != TileID.GRASS) {
					t.collisions[Thing.TOP] = true;
					t.ySpeed++;
				}
			}
			for (int i = 1; i < t.width - 1; i++) {
				if (tiles[getTileX(t.positionX + t.xSpeed + i)][getTileY(t.positionY + t.ySpeed + (t.height / 1.15))] != TileID.GRASS) {
					t.collisions[Thing.BOTTOM] = true;
					t.ySpeed--;
				}
			}
			for (int i = (int) ((t.height / 1.25) + 1); i < (t.height / 1.15) - 1; i++) {
				if (tiles[getTileX(t.positionX + t.xSpeed)][getTileY(t.positionY + t.ySpeed + i)] != TileID.GRASS) {
					t.collisions[Thing.LEFT] = true;
					t.xSpeed++;
				}
			}
			for (int i = (int) ((t.height / 1.25) + 1); i < (t.height / 1.15) - 1; i++) {
				if (tiles[getTileX(t.positionX + t.width + t.xSpeed)][getTileY(t.positionY + t.ySpeed + i)] != TileID.GRASS) {
					t.collisions[Thing.RIGHT] = true;
					t.xSpeed--;
				}
			}
		}
		while (t.collisions[Thing.TOP] || t.collisions[Thing.BOTTOM] || t.collisions[Thing.LEFT] || t.collisions[Thing.RIGHT]);
	}
}
