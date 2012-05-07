package com.game.fallborn.menu;

import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import com.game.fallborn.Game;
import com.game.fallborn.input.InputHandler;

public class Launcher extends Canvas {
	private JFrame frame;
	private InputHandler input;
	private int WIDTH = 640;
	private int HEIGHT = 480;
	private boolean running = false;
	private boolean inputAllowed = true;

	private BufferedImage menuBackground;
	private BufferedImage playButtonText;
	private int playButtonTextColor = 0xFFFFFF;
	private int[][] playButtonBounds;
	private int playButtonX = 355, playButtonY = 245;

	public Launcher() {
		loadImages();
		frame = new JFrame(Game.GAME_NAME);
		input = new InputHandler(this);
		frame.setSize(menuBackground.getWidth(), menuBackground.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		frame.add(this);
		running = true;

		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		double unprocessedSeconds = 0;
		boolean ticked = false;
		int fps = 0;
		short tickCount = 0;
		requestFocus();

		while (running) {
			long currentTime = System.nanoTime();
			unprocessedSeconds += (currentTime - previousTime) / 1000000000.0;
			previousTime = currentTime;
			while (unprocessedSeconds >= secondsPerTick) {
				unprocessedSeconds -= secondsPerTick;
				tick();
				ticked = true;
				tickCount++;
				if (tickCount % 60 == 0) {
					fps = 0;
				}
			}
			if (ticked) {
				render();
				fps++;
			}
		}
	}

	private void loadImages() {
		try {
			menuBackground = ImageIO.read(new File("res/menuBackground.png"));
			playButtonText = ImageIO.read(new File("res/playButtonText.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load launcher images");
			System.exit(1);
		}
	}

	private void tick() {
		if (inputAllowed) {
			if (input.mouseX > playButtonX && input.mouseX < playButtonX + playButtonText.getWidth() && 
				input.mouseY > playButtonY && input.mouseY < playButtonY + playButtonText.getHeight()) {
				for (int x = 0; x < playButtonText.getWidth(); x++) {
					for (int y = 0; y < playButtonText.getHeight(); y++) {
						int alpha = playButtonText.getRGB(x, y) >> 24;
						playButtonText.setRGB(x, y, (alpha << 24) + (playButtonTextColor - 0xFF));
					}
				}
				if (input.mousePressed)
					playGame();
			} else {
				for (int x = 0; x < playButtonText.getWidth(); x++) {
					for (int y = 0; y < playButtonText.getHeight(); y++) {
						int alpha = playButtonText.getRGB(x, y) >> 24;
						playButtonText.setRGB(x, y, (alpha << 24) + playButtonTextColor);
					}
				}
			}
		}
	}

	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if (bs == null) {
			createBufferStrategy(2);
			return;
		}

		Graphics g = bs.getDrawGraphics();

		g.drawImage(menuBackground, 0, 0, null);
		g.drawImage(playButtonText, playButtonX, playButtonY, null);
		g.dispose();
		bs.show();
	}

	private void playGame() {
		inputAllowed = false;
		frame.dispose();
		new Game();
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
