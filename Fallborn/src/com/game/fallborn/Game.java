package com.game.fallborn;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;

import javax.swing.JFrame;

import com.game.fallborn.input.InputHandler;
import com.game.fallborn.level.Level;
import com.game.fallborn.screen.Art;
import com.game.fallborn.screen.LightScreen;
import com.game.fallborn.screen.Screen;
import com.game.fallborn.things.alive.Player;

public class Game extends Canvas implements Runnable{
	
	// We kindly thank Mojang(or maybe it's Notch) for creating
	// this wonderful Java template for creating a game
	
	private static Game game;
	private static JFrame frame;
	private Screen screen;
	private LightScreen lightScreen;
	private Thread thread;
	private boolean running = false;
	private int fpsDisplay = 0;
	private boolean fpsLimited = true;
	
	public static final String GAME_NAME = "Fallborn";
	public Player player;
	public Level level;
	public InputHandler input;
	public Time time;
	
	public static final int GAME_WIDTH = 640;
	//Maintains a nice aspect ratio
	public static final int GAME_HEIGHT = GAME_WIDTH * 3 / 4;
	public static final int GAME_SCALE = 1;
	
	public Game() {
		frame = new JFrame(GAME_NAME);
		screen = new Screen(GAME_WIDTH, GAME_HEIGHT);
		lightScreen = new LightScreen(GAME_WIDTH, GAME_HEIGHT);
		level = new Level("res/level.png", 20);
		player = new Player(Art.fallBornSheet, 30, 60);
		input = new InputHandler(this);
		time = new Time();
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
		int fps = 0;
		short tickCount = 0;
		requestFocus();
		
		while(running) {
			long currentTime = System.nanoTime();
			unprocessedSeconds += (currentTime - previousTime) / 1000000000.0;
			previousTime = currentTime;
			while(unprocessedSeconds >= secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				tick();
				ticked = true;
				tickCount++;
				if(tickCount % 60 == 0) {
					fpsDisplay = fps;
					fps = 0;
					//System.out.println(time.getTime());
				}
			}
			
			if(ticked) {
				render();
				if(fpsLimited) ticked = false;
				fps++;
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
		player.tick(input, level, screen);
		time.tick();
	}
	
	public void render() {
		BufferStrategy bs = getBufferStrategy();
		if(bs == null) {
			createBufferStrategy(2);
			return;
		}
		
		screen.render(level, player);
		
		if(time.isNightTime()) {
			lightScreen.render(level, player, time);
		}
		
		
		Graphics g = bs.getDrawGraphics();
		
		g.drawImage(screen.image, 0, 0, GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE, null);
		
		if(time.isNightTime()) {
			g.drawImage(lightScreen.image, 0, 0, GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE, null);
		}
		
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, 80, 15);
		g.setColor(Color.GREEN);
		g.setFont(new Font("courier new", 0, 15));
		g.drawString("FPS: " + fpsDisplay, 3, 11);
		
		g.dispose();
		bs.show();
	}
	
	public static void main(String[] args) {
		game = new Game();
		
		frame.setSize(GAME_WIDTH * GAME_SCALE, GAME_HEIGHT * GAME_SCALE);
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
