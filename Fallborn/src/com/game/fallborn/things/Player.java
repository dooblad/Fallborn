package com.game.fallborn.things;

import java.awt.event.KeyEvent;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Bitmap;
import com.game.fallborn.screen.Screen;

public class Player extends Thing {
	public double theta = 0;
	public double sprintFactor = 2.0;
	
	public Player(Bitmap[][] bitmap, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.bitmap = bitmap;
		this.width = bitmap[0][0].width;
		this.height = bitmap[0][0].height;
	}

	public void tick(InputHandler input, Level level, Screen screen) {
		
		//Y movement
		if (input.keys[KeyEvent.VK_W]) {
			ySpeed = -walkSpeed;
			walkTime++;
		} else if (input.keys[KeyEvent.VK_S]) {
			ySpeed = walkSpeed;
			walkTime++;
		} else {
			ySpeed = 0;
		}
		//X movement
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
		
		float opposite = (input.mouseY - screen.height / 2);
		float adjacent = input.mouseX - screen.width / 2;
		double inverseTan = Math.toDegrees(Math.atan(opposite / adjacent));
		if(adjacent >= 0)
			theta = inverseTan;
		else {
			theta = inverseTan - 180;
		}
			
		// set animation to resting
		if (xSpeed == 0 && ySpeed == 0) {
			walkTime = 0;
		}

		// sprinting
		if (input.keys[KeyEvent.VK_SHIFT]) {
			xSpeed *= sprintFactor;
			ySpeed *= sprintFactor;
			walkTime++;
		}

		// collision detection
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

		// add calculated speed values
		positionX += xSpeed;
		positionY += ySpeed;
		
		// scrolling the world
		level.xScroll = (int) positionX - (screen.width - width) / 2;
		level.yScroll = (int) positionY - (screen.height - height) / 2;

		// add walktime controller right here!
		if(walkTime >= 10 * walkAnimationFactor) walkTime = 0;
		
		if(input.keys[KeyEvent.VK_UP]) theta += 1;
		else if(input.keys[KeyEvent.VK_DOWN]) theta -= 1;
	}

	public void render(Screen screen) {
		if (facingRight) {
			screen.blit(bitmap[walkTime / walkAnimationFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		} else if (!facingRight) {
			screen.blitReverse(bitmap[walkTime / walkAnimationFactor][0], (screen.width - width) / 2, (screen.height - height) / 2);
		}
	}

	public void addGravity() {

	}
}
