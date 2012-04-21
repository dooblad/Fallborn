package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

public class ColorScreen extends Bitmap{
	public BufferedImage image;
	private Bitmap lightOverlay = Art.lightOverlay;
	private int color;
	
	public ColorScreen(int width, int height, int color) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
		this.color = color;
		//fill(color);
	}
	
	public void fill(int color) {
		int result; 
		
		int colorRed = (color << 16) & 0xFF;
		int colorGreen = (color << 8) & 0xFF;
		int colorBlue = color & 0xFF;
		for(int i = 0; i < lightOverlay.pixels.length; i++) {
			int lightAlpha = (lightOverlay.pixels[i] << 24) & 0xFF;
			int lightRed = (lightOverlay.pixels[i] << 16) & 0xFF;
			int lightGreen = (lightOverlay.pixels[i] << 8) & 0xFF;
			int lightBlue = lightOverlay.pixels[i] & 0xFF;
			
			if((lightRed + colorRed) > 255) lightRed = 255;
			else lightRed += colorRed;
			
			if((lightGreen + colorGreen) > 255) lightGreen = 255;
			else lightGreen += colorGreen;
			
			if((lightBlue + colorBlue) > 255) lightBlue = 255;
			else lightBlue += colorBlue;
			
			pixels[i] = (lightAlpha >> 24) + (lightRed >> 16) + (lightGreen >> 8) + lightBlue;
			
			System.out.println(lightOverlay.pixels[i]);
		}
	}
	
	public void render() {
		for(int i = 0; i < pixels.length; i++) {
			pixels[i] = lightOverlay.pixels[i];
		}
	}

}
