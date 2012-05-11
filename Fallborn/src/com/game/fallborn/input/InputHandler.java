package com.game.fallborn.input;

import java.awt.Component;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener{
	public boolean[] keys = new boolean[128];
	public int oldMouseX = 0;
	public int mouseX = 0;
	public int oldMouseY = 0;
	public int mouseY = 0;
	public boolean mousePressed = false;
	
	public InputHandler(Component c) {
		c.addKeyListener(this);
		c.addMouseListener(this);
		c.addMouseMotionListener(this);
	}
	
	public void tick() {
		//oldMouseX = mouseX;
		//oldMouseY = mouseY;
	}
	
	//KEY EVENTS
	public void keyPressed(KeyEvent e) {
		keys[e.getKeyCode()] = true;
	}

	public void keyReleased(KeyEvent e) {
		keys[e.getKeyCode()] = false;
	}

	public void keyTyped(KeyEvent e) {
		
	}
	
	//MOUSE MOTION
	public void mouseDragged(MouseEvent e) {
		
	}

	public void mouseMoved(MouseEvent e) {
		mouseX = e.getX();
		mouseY = e.getY();
	}
	
	//MOUSE EVENTS
	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
		
	}

	public void mouseExited(MouseEvent e) {
		
	}

	public void mousePressed(MouseEvent e) {
		if(!mousePressed) {
			oldMouseX = e.getX();
			oldMouseY = e.getY();
		}
		mouseX = e.getX();
		mouseY = e.getY();
		mousePressed = true;
	}

	public void mouseReleased(MouseEvent e) {
		mousePressed = false;
	}


}
