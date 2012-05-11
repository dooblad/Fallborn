package com.game.fallborn.things.inventory;

import com.game.fallborn.screen.Art;
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
				screen.blit(Art.inventorySelected, 0, ((screen.height - Art.inventorySlot.height * size) / 2) + i * slotSize);
			} else {
				screen.blit(Art.inventorySlot, 0, ((screen.height - Art.inventorySlot.height * size) / 2) + i * slotSize);
			}
		}
	}
	
	public int getSelected() {
		return selected;
	}
	public void setSelected(int selected) {
		this.selected = selected;
	}
	
	public int getSize() {
		return size;
	}
	public void setSize(int size) {
		this.size = size;
	}
}
