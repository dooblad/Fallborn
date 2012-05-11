package com.game.fallborn.things.magic;

import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.Screen;
import com.game.fallborn.sound.Sound;
import com.game.fallborn.things.Direction;
import com.game.fallborn.things.alive.Player;

public class FireBall extends Spell{
	public int currentSpellLife = 0;
	public static int maxSpellLife = 30;
	private int xAcceleration = 0;
	private int yAcceleration = 0;
	public static int spellCooldown = 10;
	
	public FireBall() {
		this.width = Art.fireBall.width;
		this.height = Art.fireBall.height;
	}
	
	public void castSpell(Player player, Screen screen, Direction direction) {
		positionX = player.positionX;
		positionY = player.positionY;
		player.livingSpells.add(this);
		player.spellCooldown = spellCooldown;
		this.direction = direction;
		//Sound.playSound(Sound.fireSpell);
	}
	
	public void tick(Level level, Player player) {
		currentSpellLife++;
		switch(this.direction) {
		case UP: ySpeed = --yAcceleration;
		break;
		case DOWN: ySpeed = ++yAcceleration;
		break;
		case LEFT: xSpeed = --xAcceleration;
		break;
		case RIGHT: xSpeed = ++xAcceleration;
		break;
		default: xSpeed = ++xAcceleration;
		}
		//level.collided(this);
	    positionX += xSpeed;
		positionY += ySpeed;
	}
	public void render(Level level, Screen screen, Player player) {
		screen.blit(Art.fireBall, (int) positionX - level.getXScroll(), (int) positionY - level.getYScroll());
	}
}
