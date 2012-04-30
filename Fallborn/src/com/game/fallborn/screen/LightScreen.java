package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.level.*;
import com.game.fallborn.things.Player;

public class LightScreen extends Bitmap {
	public BufferedImage image;
	private double radius = 200;
	private double angleSpread = 60;
	private double lowAngle, highAngle;

	public LightScreen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public void render(Level level, Player player) {
		fill(0xFA000000);
		lowAngle = player.theta - (angleSpread / 2);
		highAngle = player.theta + (angleSpread / 2);
		int alphaIncrement = (int) (0xFF / radius);

		for (double t = lowAngle; t <= highAngle; t += 0.15) { // decrease increment for finer shadows
			for (int r = 0; r < radius; r++) {
				int x = (int) (Math.cos(Math.toRadians(t)) * r);
				int y = (int) (Math.sin(Math.toRadians(t)) * r);
				
				int xx = x + (width / 2);
				int yy = y + (height / 2 - (player.getHeight() / 2));
				if (xx < 0 || xx >= width || yy < 0 || yy >= height)
					continue;
				//System.out.println("X: " + x + " Y: " + y);
				if(level.tiles[level.getTileX(player.getPositionX() + xx)][level.getTileY(player.getPositionY() + yy)] != TileID.GRASS) continue;
				
				pixels[xx + yy * width] = 0x00000000 + ((alphaIncrement * ((int) (r + (radius / 3.7 - 0xF)))) << 24);
			}
		}
	}
}
