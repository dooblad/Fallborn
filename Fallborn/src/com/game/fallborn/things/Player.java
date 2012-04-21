package com.game.fallborn.things;

import java.awt.event.KeyEvent;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Bitmap;
import com.game.fallborn.screen.Screen;

public class Player extends Thing {

	public Player(Bitmap[][] bitmap, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.bitmap = bitmap;
		this.width = bitmap[0][0].width;
		this.height = bitmap[0][0].height;
	}

	public void tick(InputHandler input, Level level, Screen screen) {

		if (input.keys[KeyEvent.VK_W]) {
			ySpeed = -walkSpeed;
			walkTime++;
		}
		else if (input.keys[KeyEvent.VK_S]) {
			ySpeed = walkSpeed;
			walkTime++;
		}
		else {
			ySpeed = 0;
		}

		if (input.keys[KeyEvent.VK_A]) {
			xSpeed = -walkSpeed;
			walkTime++;
		}
		else if (input.keys[KeyEvent.VK_D]) {
			xSpeed = walkSpeed;
			walkTime++;
		}
		else {
			xSpeed = 0;
		}
		
		if(xSpeed == 0 && ySpeed == 0) {
			walkTime = 0;
		}

		if (input.keys[KeyEvent.VK_SHIFT]) {
			xSpeed *= sprintFactor;
			ySpeed *= sprintFactor;
		}
		
		level.collided(this);
		
		if(collisions[Thing.TOP] && ySpeed < 0) {
			ySpeed = 0;
		}
		if(collisions[Thing.BOTTOM] && ySpeed > 0) {
			ySpeed = 0;
		}
		if(collisions[Thing.LEFT] && xSpeed < 0) {
			xSpeed = 0;
		}
		if(collisions[Thing.RIGHT] && xSpeed > 0) {
			xSpeed = 0;
		}
		
		positionX += xSpeed;
		positionY += ySpeed;
		
		//System.out.println("X: " + positionX + " Y: " + positionY);
		
		level.xScroll = (int) positionX - (screen.width - width) / 2;
		level.yScroll = (int) positionY - (screen.height - height) / 2;
		
		//add walktime controller right here!
	}

	public void render(Screen screen) {
		screen.blit(bitmap[0][0], (screen.width - width) / 2, (screen.height - height) / 2 /*(int) (positionX + 0.5), (int) (positionY + 0.5)*/);
	}
	
	public void addGravity() {
		
	}
}
