package com.game.GUI;

import com.game.fallborn.Game;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.MainScreen;
import com.game.fallborn.screen.Screen;
import com.game.fallborn.things.alive.Player;

public class Inventory {
	private int slotSize = Art.inventorySlot.height;
	private int size;
	private int selected = 0;
	
	public Inventory(int size) {
		this.size = size;
	}
	
	public void tick() {
		
	}
	
	public void render(Screen screen) {
		for(int i = 0; i < size; i++) {
			if(i == selected) {
				screen.blit(Art.inventorySelected, 0, ((Game.GAME_HEIGHT * Game.GAME_SCALE - Art.inventorySlot.height * size) / 2) + i * slotSize);
			} else {
				screen.blit(Art.inventorySlot, 0, ((Game.GAME_HEIGHT * Game.GAME_SCALE - Art.inventorySlot.height * size) / 2) + i * slotSize);
			}
		}
	}
	
	public int getSlotSize() {
		return slotSize;
	}
	public void setSlotSize(int slotSize) {
		this.slotSize = slotSize;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
	
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
}
