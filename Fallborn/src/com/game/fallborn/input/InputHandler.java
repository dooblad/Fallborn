package com.game.fallborn.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import com.game.fallborn.Game;

public class InputHandler implements KeyListener {
	public boolean[] keys = new boolean[128];
	
	public InputHandler(Game game) {
		game.addKeyListener(this);
	}
	
	public void tick() {

	}
	
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}

}
