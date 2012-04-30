package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.level.*;
import com.game.fallborn.things.Player;

public class LightScreen extends Bitmap {
	public BufferedImage image;
	private double radius = 150;
	private double angleSpread = 120;
	private double lowAngle, highAngle;

	public LightScreen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public void render(Level level, Player player) {
		fill(0xD0000000);
		lowAngle = player.theta - (angleSpread / 2);
		highAngle = player.theta + (angleSpread / 2);
		float alphaIncrement = (float) (0x90 / radius);

		for (double t = lowAngle; t <= highAngle; t += 0.15) { // decrease increment for finer shadows
			for (int r = 0; r < radius; r++) {
				int x = (int) (Math.cos(Math.toRadians(t)) * r);
				int y = (int) (Math.sin(Math.toRadians(t)) * r);
				
				int xx = x + (width / 2);
				int yy = y + (height / 2 - (player.getHeight() / 2));
				if (xx < 0 || xx >= width || yy < 0 || yy >= height)
					continue;
				double shadowPositionX = player.getPositionX() + xx - (width - player.width) / 2;
				double shadowPositionY = player.getPositionY() + yy - (height - player.height) / 2;
				
				if(shadowPositionX < 0 || shadowPositionX >= level.width * level.tileSize ||
				   shadowPositionY < 0 || shadowPositionY >= level.height * level.tileSize) continue;
				
				if(level.tiles[level.getTileX(shadowPositionX)]
				  [level.getTileY(shadowPositionY)] != TileID.GRASS) break;
				
				pixels[xx + yy * width] = ((int)(alphaIncrement * (r + radius / 3.7)) << 24);
			}
		}
	}
}
