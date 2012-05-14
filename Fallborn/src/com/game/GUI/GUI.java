package com.game.GUI;

import com.game.fallborn.screen.Screen;

public class GUI extends Screen {
	public Inventory inventory;
	
	public GUI(int width, int height, int inventorySize) {
		super(width, height);
		inventory = new Inventory(inventorySize);
	}
	
	public void tick() {
		inventory.tick();
	}
	
	public void render() {
		fill(0);
		inventory.render(this);
	}
}
