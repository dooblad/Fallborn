package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.Time;
import com.game.fallborn.level.*;
import com.game.fallborn.things.alive.Player;;

public class LightScreen extends Bitmap {
	public BufferedImage image;
	private double radius = 150;
	private double angleSpread = 120;
	private double lowAngle, highAngle;

	private double nightAlphaIncrement;
	private int maxNightAlpha = 0xE9;
	private int nightColor = 0x00000B;
	private int currentNightAlpha = 0;
	
	//private int lightColor = 0x100606;
	//private float lightAlphaIncrement = (float) (0x91 / (radius));

	
	/**
	 * @param width specifies the width of this screen
	 * @param height specifies the height of this screen
	 */
	public LightScreen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}

	public void render(Level level, Player player, Time time) {
		nightAlphaIncrement = ((double) (currentNightAlpha)) / radius;
		long timeUntilDay = time.getDayLength() - time.getTime();
		currentNightAlpha = (int) (Math.sin((time.getNightLength() - timeUntilDay) / (time.getNightLength() / time.getDayToNightRatio())) * (double) maxNightAlpha);
		//System.out.println((int) (Math.sin((time.getNightLength() - timeUntilDay) / (time.getNightLength() / time.getDayToNightRatio())) * (double) maxNightAlpha));
		
		if(currentNightAlpha > maxNightAlpha) currentNightAlpha = maxNightAlpha;
		fill((currentNightAlpha << 24) + nightColor);
		
		if (player.getLightIsOn()) {
			lowAngle = player.theta - (angleSpread / 2);
			highAngle = player.theta + (angleSpread / 2);
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

					if (shadowPositionX < 0 || shadowPositionX >= level.width * level.tileSize || shadowPositionY < 0 || shadowPositionY >= level.height * level.tileSize)
						continue;

					if (level.tiles[level.getTileX(shadowPositionX)][level.getTileY(shadowPositionY)] != TileID.GRASS)
						break;

					int pixel = ((int) (nightAlphaIncrement * r) << 24) + nightColor;
					pixels[xx + yy * width] = pixel;
				}
			}
		}
	}
}
