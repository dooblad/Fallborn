package com.game.fallborn;

import java.awt.Canvas;

import javax.swing.JFrame;

import com.game.fallborn.screen.Screen;

public class Game extends Canvas implements Runnable{
	
	//We kindly thank Mojang(or maybe it's Notch) for creating
	//this wonderful Java template for creating a game
	
	private static Game game;
	private static JFrame frame;
	private Screen screen;
	private Thread thread;
	
	public static final String GAME_NAME = "Fallborn";
	
	public static final int GAME_WIDTH = 320;
	//Maintains a nice aspect ratio
	public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
	public static final int GAME_SCALE = 2;
	
	public Game() {
		frame = new JFrame(GAME_NAME);
		screen = new Screen(GAME_WIDTH, GAME_HEIGHT);
	}
	
	public void start() {
		
	}
	public void run() {
		
	}
	public void stop() {
		
	}
	
	public void tick() {
		
	}
	public void render() {
		
	}
	
	public static void main(String[] args) {
		game = new Game();
		
		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		System.out.println("Pseudo-lucidity at its finest");
		game.start();
	}
}
