package com.game.fallborn.things;

import java.awt.event.KeyEvent;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Screen;

public class Player extends Thing {

	public Player(int positionX, int positionY) {
		this.positionX = positionX;
		this.positionY = positionY;
	}

	public void tick(InputHandler input) {

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
		System.out.println("XSpeed = " + xSpeed + " YSpeed = " + ySpeed);

		positionX += xSpeed;
		positionY += ySpeed;
	}

	public void render(Screen screen) {
		screen.blit(Art.fallBorn, (int) (positionX + 0.5), (int) (positionY + 0.5));
	}
}
