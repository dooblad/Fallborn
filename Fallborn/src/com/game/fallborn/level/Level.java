package com.game.fallborn.level;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Screen;

public class Level {
	public int[][] tiles;
	public int width, height;
	public int tileSize;
	
	public Level(String URL, int tileSize) {
		try {
			BufferedImage image = ImageIO.read(new File(URL));
			this.tileSize = tileSize;
			width = image.getWidth();
			height = image.getHeight();
			tiles = new int[width][height];
			
			for(int x = 0; x < width; x++) {
				for(int y = 0; y < height; y++) {
					int pixel = image.getRGB(x, y);
				    int red = (pixel >> 16) & 0xff;
				    int green = (pixel >> 8) & 0xff;
				    int blue = (pixel) & 0xff;
				    tiles[x][y] = blue + (green << 8) + (red << 16);
				    System.out.println(tiles[x][y]);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public Level(String URL, int width, int height, int tileSize) {
		
	}
	public Level() {
		//eventually semi-random
	}
	
	public void render(Screen screen) {
		//eventually only render the visible area
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				switch(tiles[x][y]) {
				case LevelColors.GRASS: 
					System.out.println("[" + x + "][" + y + "]");
					screen.blit(Art.tiles[0][0], x * tileSize,y * tileSize); 
					break;
				case LevelColors.SHRUB: 
					System.out.println("[" + x + "][" + y + "]" + "   A: " + 0 * tileSize);
					screen.blit(Art.tiles[1][0], x * tileSize, y * tileSize); 
					break;
				default: continue;
				}
			}
		}
	}
}
