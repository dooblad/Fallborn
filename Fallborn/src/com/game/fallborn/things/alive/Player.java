package com.game.fallborn.things.alive;

import java.awt.event.KeyEvent;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Bitmap;
import com.game.fallborn.screen.Screen;
import com.game.fallborn.things.Thing;

public class Player extends LivingThing {
	public double theta = 0;
	public double sprintFactor = 2.0;
	public boolean lightIsOn = false;
	private boolean lightToggled = false;
	
	public Player(Bitmap[][] bitmap, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.bitmap = bitmap;
		this.width = bitmap[0][0].width;
		this.height = bitmap[0][0].height;
	}

	public void tick(InputHandler input, Level level, Screen screen) {
		
		// Y Movement
		if (input.keys[KeyEvent.VK_W]) {
			ySpeed = -walkSpeed;
			walkTime++;
		} else if (input.keys[KeyEvent.VK_S]) {
			ySpeed = walkSpeed;
			walkTime++;
		} else {
			ySpeed = 0;
		}
		// X Movement
		if (input.keys[KeyEvent.VK_A]) {
			xSpeed = -walkSpeed;
			walkTime++;
			facingRight = false;
		} else if (input.keys[KeyEvent.VK_D]) {
			xSpeed = walkSpeed;
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
		
		// Mouse Computing
		float opposite = input.mouseY - screen.height / 2;
		float adjacent = input.mouseX - screen.width / 2;
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

		// Collision Detection
		level.collided(this);

		if (collisions[Thing.TOP] && ySpeed < 0) {
			ySpeed = 0;
		}
		if (collisions[Thing.BOTTOM] && ySpeed > 0) {
			ySpeed = 0;
		}
		if (collisions[Thing.LEFT] && xSpeed < 0) {
			xSpeed = 0;
		}
		if (collisions[Thing.RIGHT] && xSpeed > 0) {
			xSpeed = 0;
		}

		// Add Calculated Speed Values
		positionX += xSpeed;
		positionY += ySpeed;
		
		// Scrolling the World
		level.setXScroll((int) positionX - (screen.width - width) / 2);
		level.setYScroll((int) positionY - (screen.height - height) / 2);

		// Walktime Resetter
		if(walkTime >= 10 * animationSpeedFactor) walkTime = 0;
	}

	public void render(Screen screen) {
		if (facingRight) {
			screen.blit(bitmap[walkTime / animationSpeedFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		} else if (!facingRight) {
			screen.blitReverse(bitmap[walkTime / animationSpeedFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		}
	}
	
	public boolean getLightIsOn() {
		return lightIsOn;
	}
}
