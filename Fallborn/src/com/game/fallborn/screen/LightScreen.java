package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.Time;
import com.game.fallborn.level.*;
import com.game.fallborn.things.alive.Player;;

public class LightScreen extends Screen {
	private double radius = 100;
	private double angleSpread = 100;
	private double lowAngle, highAngle;

	private double nightAlphaIncrement;
	private int maxNightAlpha = 0xE9;
	private int nightColor = 0x00000B;
	private int currentNightAlpha = 0;
	
	/**
	 * @param width specifies the width of this screen
	 * @param height specifies the height of this screen
	 */
	public LightScreen(int width, int height) {
		super(width, height);
	}

	public void render(Level level, Player player, Time time) {
		nightAlphaIncrement = (currentNightAlpha) / radius;
		long timeUntilDay = time.getDayLength() - time.getTime();
		currentNightAlpha = (int) (Math.sin((time.getNightLength() - timeUntilDay) / (time.getNightLength() / time.getDayToNightRatio())) * maxNightAlpha);
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

					if (shadowPositionX < 0 || shadowPositionX >= level.getWidth() * level.getTileSize() || shadowPositionY < 0 || shadowPositionY >= level.getHeight() * level.getTileSize())
						continue;

					if (level.tiles[level.getTileX(shadowPositionX /*+ (x < 0 ? level.getTileSize() : -level.getTileSize())*/)][level.getTileY(shadowPositionY /*+ (y < 0 ? level.getTileSize() : -level.getTileSize())*/)] != TileID.GRASS)
						break;

					int pixel = ((int) (nightAlphaIncrement * r) << 24) + nightColor;
					pixels[xx + yy * width] = pixel;
				}
			}
		}
	}
	
	public double getRadius() {
		return radius;
	}
	public void setRadius(double radius) {
		this.radius = radius;
	}
	
	public double getAngleSpread() {
		return angleSpread;
	}
	public void setAngleSpread(double angleSpread) {
		this.angleSpread = angleSpread;
	}
}
