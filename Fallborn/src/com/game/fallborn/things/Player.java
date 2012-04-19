package com.game.fallborn.things;

import java.awt.event.KeyEvent;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Bitmap;
import com.game.fallborn.screen.Screen;

public class Player extends Thing {

	public Player(Bitmap bitmap, int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
		this.bitmap = bitmap;
		this.width = bitmap.width;
		this.height = bitmap.height;
	}

	public void tick(InputHandler input, Level level) {

		if (input.keys[KeyEvent.VK_W])
			ySpeed = -walkSpeed;
		else if (input.keys[KeyEvent.VK_S])
			ySpeed = walkSpeed;
		else
			ySpeed = 0;

		if (input.keys[KeyEvent.VK_A])
			xSpeed = -walkSpeed;
		else if (input.keys[KeyEvent.VK_D])
			xSpeed = walkSpeed;
		else
			xSpeed = 0;

		if (input.keys[KeyEvent.VK_SHIFT]) {
			xSpeed *= sprintFactor;
		}
		
		level.collided(this);
		
		/*if(collisions[Thing.TOP] && collisions[Thing.LEFT] && collisions[Thing.RIGHT] && ySpeed > 0) {
			ySpeed = 0;
		}*/
		if(collisions[Thing.TOP] && ySpeed > 0) {
			ySpeed = 0;
		}
		/*if(collisions[Thing.BOTTOM] && collisions[Thing.LEFT] && collisions[Thing.RIGHT] && ySpeed < 0) {
			ySpeed = 0;
		}*/
		if(collisions[Thing.BOTTOM] && ySpeed < 0) {
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
	}

	public void render(Screen screen) {
		screen.blit(bitmap, (int) (positionX + 0.5), (int) (positionY + 0.5));
	}
	
	public void addGravity() {
		
	}
}
