package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;

import com.game.fallborn.level.Level;
import com.game.fallborn.things.Player;

public class Screen extends Bitmap{
	public BufferedImage image;
	
	public Screen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void render(Level level, Player player) {
		fill(0xFF1F1F1F);
		//blit(Art.tiles[0][0], 40, 40);
		level.render(this);
		player.render(this);
	}
}
