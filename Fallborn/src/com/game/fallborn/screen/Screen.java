package com.game.fallborn.screen;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.ArrayList;
import java.util.List;

import com.game.fallborn.level.Level;
import com.game.fallborn.things.alive.Player;

public class Screen extends Bitmap{
	public BufferedImage image;
	public List<Integer> renderOrder = new ArrayList<Integer>();
	
	public Screen(int width, int height) {
		super(width, height);
		image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		pixels = ((DataBufferInt) image.getRaster().getDataBuffer()).getData();
	}
	
	public void render(Level level, Player player) {
		fill(0xFF000000);
		level.render(this);
		player.render(this);
	}
}
