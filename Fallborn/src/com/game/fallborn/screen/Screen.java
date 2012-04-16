package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.things.Player;

public class Screen extends Bitmap{
	public BufferedImage image;
	
	public Screen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void render(Player player) {
		fill(0xFF0044D6);
		//blit(Art.sheetTest[1][0], 10, 10);
		player.render(this);
	}
}
