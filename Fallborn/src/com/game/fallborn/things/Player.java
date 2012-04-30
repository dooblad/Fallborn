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
		
		
		//set theta for primary directions(lighting)
		if(input.keys[KeyEvent.VK_W]) theta = 270;
		else if(input.keys[KeyEvent.VK_S]) theta = 90;
		if(input.keys[KeyEvent.VK_A]) theta = 180;
		else if(input.keys[KeyEvent.VK_D]) theta = 0;
		
		//set theta for intermediate directions
		if(input.keys[KeyEvent.VK_D] && input.keys[KeyEvent.VK_W]) theta = 315;
		else if(input.keys[KeyEvent.VK_W] && input.keys[KeyEvent.VK_A]) theta = 215;
		else if(input.keys[KeyEvent.VK_A] && input.keys[KeyEvent.VK_S]) theta = 135;
		else if(input.keys[KeyEvent.VK_S] && input.keys[KeyEvent.VK_D]) theta = 45;

			
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
		
		System.out.println("X: " + positionX + " Y: " + positionY);

		// scrolling the world
		level.xScroll = (int) positionX - (screen.width - width) / 2;
		level.yScroll = (int) positionY - (screen.height - height) / 2;

		// add walktime controller right here!
		if(walkTime >= 10 * walkAnimationFactor) walkTime = 0;
		
		if(input.keys[KeyEvent.VK_UP]) theta+=0.1;
		else if(input.keys[KeyEvent.VK_DOWN]) theta-=0.1;
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
