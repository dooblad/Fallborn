package com.game.fallborn;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.game.fallborn.screen.Screen;

public class Game extends Canvas implements Runnable{
	
	//We kindly thank Mojang(or maybe it's Notch) for creating
	//this wonderful Java template for creating a game
	
	private static Game game;
	private static JFrame frame;
	private Screen screen;
	private Thread thread;
	private boolean running = false;
	
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
		if(!running) {
		thread = new Thread(this);
		running = true;
		thread.start();
		}
	}
	public void run() {
		while(running) {
		render();
		}
	}
	public void stop() {
		if(running) {
			try {
				thread.join();
			} catch(InterruptedException e) {
				e.printStackTrace();
				System.exit(0);
			}
		}
	}
	
	public void tick() {
		
	}
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		screen.render();
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(screen.image, 0, 0, null);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		game = new Game();
		
		frame.setSize(GAME_WIDTH, GAME_HEIGHT);
		frame.setAlwaysOnTop(true);
		frame.setFocusable(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		
		System.out.println("Pseudo-lucidity at its finest");
		game.start();
	}
}
