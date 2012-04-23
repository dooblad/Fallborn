package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.things.Player;

public class LightScreen extends Bitmap {
	public BufferedImage image;
	private double radius = 100;
	private double angleSpread = 360;
	private double lowAngle, highAngle;
	
	
	public LightScreen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void render(Player player) {
		fill(0);
		lowAngle = player.theta - (angleSpread / 2);
		highAngle = player.theta + (angleSpread / 2);
		
		int alphaIncrement = (int) (0xFF / radius);
		for(double t = lowAngle; t < highAngle; t++) {
			for(int r = 0; r < radius; r++) {
				int x = (int) (Math.cos(t) * r);
				int y = (int) (Math.sin(t) * r);
				//System.out.println("X: " + Math.cos(t) * r + " Y: " + Math.sin(t) * r);
				//if(x < 0 || x >= width || y < 0 || y >= height) continue;
				int xx = x + (width / 2);
				int yy = y + (height / 2);
				if(xx < 0 || xx >= width || yy < 0 || yy >= height) continue;
				pixels[xx + yy * width] = 0xFF0000FF - ((alphaIncrement * r) << 24);
			}
		}
		System.out.println("rendered");
	}
}
