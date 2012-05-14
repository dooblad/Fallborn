package com.game.fallborn.things.alive;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

import com.game.GUI.GUI;
import com.game.GUI.Inventory;
import com.game.fallborn.Game;
import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Bitmap;
import com.game.fallborn.screen.MainScreen;
import com.game.fallborn.things.Direction;
import com.game.fallborn.things.Thing;
import com.game.fallborn.things.magic.FireBall;
import com.game.fallborn.things.magic.Spell;
import com.game.fallborn.things.magic.Spells;

public class Player extends LivingThing {
	public double theta = 0;
	public double sprintFactor = 2.0;
	public boolean lightIsOn = false;
	private boolean lightToggled = false;
	public boolean[] knownSpells = new boolean[Spells.numberOfSpells];
	public List<Spell> livingSpells = new ArrayList<Spell>();
	public int spellCooldown = 0;
	public GUI gui;
	
	public Player(Bitmap[][] bitmap, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.bitmap = bitmap;
		this.width = bitmap[0][0].width;
		this.height = bitmap[0][0].height;
		knownSpells[Spells.FIRE_BALL.index] = true;
		gui = new GUI(Game.GAME_WIDTH * Game.GAME_SCALE, Game.GAME_HEIGHT * Game.GAME_SCALE, 4);
	}

	public void tick(InputHandler input, Level level, MainScreen screen) {
		// Y Movement
		if (input.keys[KeyEvent.VK_W]) {
			ySpeed = -walkSpeed;
			direction = Direction.UP;
			walkTime++;
		} else if (input.keys[KeyEvent.VK_S]) {
			ySpeed = walkSpeed;
			direction = Direction.DOWN;
			walkTime++;
		} else {
			ySpeed = 0;
		}
		// X Movement
		if (input.keys[KeyEvent.VK_A]) {
			xSpeed = -walkSpeed;
			direction = Direction.LEFT;
			walkTime++;
			facingRight = false;
		} else if (input.keys[KeyEvent.VK_D]) {
			xSpeed = walkSpeed;
			direction = Direction.RIGHT;
			walkTime++;
			facingRight = true;
		} else {
			xSpeed = 0;
		}
		
		// Light Toggler
		if(input.keys[KeyEvent.VK_F] && !lightToggled) {
			lightIsOn = !lightIsOn;
			lightToggled = true;
		} else if(!input.keys[KeyEvent.VK_F]) {
			lightToggled = false;
		}
		
		// Testing Inventory
		if(input.keys[KeyEvent.VK_UP]) {
			gui.inventory.setSize(gui.inventory.getSize() + 1);
		}
		else if (input.keys[KeyEvent.VK_DOWN]) {
			gui.inventory.setSize(gui.inventory.getSize() - 1);
		}
		
		// Inventory slot selection
		if(input.keys[KeyEvent.VK_1]) {
			gui.inventory.setSelected(0); // 'tis 0 because of ye olde array indices beginneth at 0
		}
		else if(input.keys[KeyEvent.VK_2]) {
			gui.inventory.setSelected(1);
		}
		else if(input.keys[KeyEvent.VK_3]) {
			gui.inventory.setSelected(2);
		}
		else if(input.keys[KeyEvent.VK_4]) {
			gui.inventory.setSelected(3);
		}
		
		// Mouse Computing
		float opposite = input.mouseY - screen.height * Game.GAME_SCALE / 2;
		float adjacent = input.mouseX - screen.width * Game.GAME_SCALE / 2;
		double inverseTan = Math.toDegrees(Math.atan(opposite / adjacent));
		if(adjacent >= 0)
			theta = inverseTan;
		else {
			theta = inverseTan - 180;
		}
			
		// Set Animation to Resting
		if (xSpeed == 0 && ySpeed == 0) {
			walkTime = 0;
		}

		// Sprinting
		if (input.keys[KeyEvent.VK_SHIFT]) {
			xSpeed *= sprintFactor;
			ySpeed *= sprintFactor;
			walkTime++;
		}
		
		// Spell Casting
		if(spellCooldown > 0) spellCooldown--;
		if (input.keys[KeyEvent.VK_SPACE] && spellCooldown == 0) {
			new FireBall().castSpell(this, screen, direction);
		}
		for(Spell spell : livingSpells) {
			if(spell instanceof FireBall) {
				((FireBall) spell).tick(level, this);
			}
		}

		// Collision Detection
		level.collided(this);

		// Add Calculated Speed Values
		positionX += xSpeed;
		positionY += ySpeed;
		
		// Scrolling the World
		level.setXScroll((int) positionX - (screen.width - width) / 2);
		level.setYScroll((int) positionY - (screen.height - height) / 2);

		// Walktime Resetter
		if(walkTime >= 10 * animationSpeedFactor) walkTime = 0;
	}

	public void render(Level level, MainScreen screen) {
		if (facingRight) {
			screen.blit(bitmap[walkTime / animationSpeedFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		} else if (!facingRight) {
			screen.blitReverse(bitmap[walkTime / animationSpeedFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		}
		
		for(int i = 0; i < livingSpells.size(); i++) {
			if(livingSpells.get(i) instanceof FireBall) {
				if(((FireBall) livingSpells.get(i)).currentSpellLife > FireBall.maxSpellLife)
					livingSpells.remove(i);
				else
					((FireBall) livingSpells.get(i)).render(level, screen, this);	
			}
		}
	}
	
	public boolean getLightIsOn() {
		return lightIsOn;
	}
}
