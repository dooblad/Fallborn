package com.game.fallborn;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.screen.Screen;
import com.game.fallborn.things.Player;

public class Game extends Canvas implements Runnable{
	
	//We kindly thank Mojang(or maybe it's Notch) for creating
	//this wonderful Java template for creating a game
	
	private static Game game;
	private static JFrame frame;
	private Screen screen;
	private Thread thread;
	private boolean running = false;
	
	public static final String GAME_NAME = "Fallborn";
	public Player player;
	public InputHandler input;
	
	public static final int GAME_WIDTH = 320;
	//Maintains a nice aspect ratio
	public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
	public static final int GAME_SCALE = 2;
	
	public Game() {
		frame = new JFrame(GAME_NAME);
		screen = new Screen(GAME_WIDTH, GAME_HEIGHT);
		player = new Player(30, 30);
		input = new InputHandler(this);
	}
	
	public void start() {
		if(!running) {
		thread = new Thread(this);
		running = true;
		thread.start();
		}
	}
	public void run() {
		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		double unprocessedSeconds = 0;
		boolean ticked = false;
		requestFocus();
		
		while(running) {
			long currentTime = System.nanoTime();
			unprocessedSeconds += (currentTime - previousTime) / 1000000000.0;
			previousTime = currentTime;
			while(unprocessedSeconds >= secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				tick();
				ticked = true;
			}
			
			if(ticked) {
				render();
			}
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
		//input.tick();
		player.tick(input);
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		screen.render(player);
		
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
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.add(game);
		frame.setVisible(true);
		
		System.out.println("Pseudo-lucidity at its finest");
		game.start();
	}
}
