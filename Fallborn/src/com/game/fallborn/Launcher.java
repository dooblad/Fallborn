package com.game.fallborn;

import static org.lwjgl.openal.AL10.*;
import java.awt.Canvas;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.IntBuffer;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.AL;
import org.lwjgl.util.WaveData;

import com.game.fallborn.input.InputHandler;

public class Launcher extends Canvas {
	private JFrame frame;
	private Point frameLocation;
	private InputHandler input;
	private boolean running = false;
	private boolean inputAllowed = true;

	private BufferedImage menuBackground;

	private int textColorDefault = 0xFFFFFF;

	private BufferedImage playButtonText;
	private int playButtonX = 355, playButtonY = 187;

	private BufferedImage quitButtonText;
	private int quitButtonX = 355, quitButtonY = 281;

	private IntBuffer menuMusicBuffer;
	private IntBuffer menuMusicSource;
	private boolean musicPlaying = false;
	

	public Launcher() {
		loadImages();
		loadMusic();
		frame = new JFrame(Game.GAME_NAME);
		input = new InputHandler(this);
		frame.setSize(menuBackground.getWidth(), menuBackground.getHeight());
		frame.setLocationRelativeTo(null);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setAlwaysOnTop(true);
		frame.setResizable(false);
		frame.add(this);
		frame.setUndecorated(true);
		frame.setVisible(true);
		running = true;

		playMusic();

		long previousTime = System.nanoTime();
		double secondsPerTick = 1 / 60.0;
		double unprocessedSeconds = 0;
		boolean ticked = false;
		int fps = 0;
		short tickCount = 0;

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
			quitButtonText = ImageIO.read(new File("res/quitButtonText.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.err.println("Could not load launcher images");
			System.exit(1);
		}
	}

	private void loadMusic() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			AL.destroy();
			e.printStackTrace();
			System.err.println("Could not initialize OpenAL");
			System.exit(1);
		}
		
		WaveData data = WaveData.create("menu.wav");
		menuMusicBuffer = BufferUtils.createIntBuffer(1);
		alGenBuffers(menuMusicBuffer);
		alBufferData(menuMusicBuffer.get(0), data.format, data.data, data.samplerate);
		menuMusicSource = BufferUtils.createIntBuffer(1);
		alGenSources(menuMusicSource);
		alSourcei(menuMusicSource.get(0), AL_BUFFER, menuMusicBuffer.get(0));
	}

	private void playMusic() {
		alSourcePlay(menuMusicSource);
		musicPlaying = true;
	}

	private void tick() {
		input.tick();
		
		if (inputAllowed) {
			if (input.mouseX > playButtonX && input.mouseX < playButtonX + playButtonText.getWidth() &&
				input.mouseY > playButtonY && input.mouseY < playButtonY + playButtonText.getHeight()) {
				for (int x = 0; x < playButtonText.getWidth(); x++) {
					for (int y = 0; y < playButtonText.getHeight(); y++) {
						int alpha = playButtonText.getRGB(x, y) >> 24;
						playButtonText.setRGB(x, y, (alpha << 24) + (textColorDefault - 0xFF));
					}
				}
				if (input.mousePressed)
					playGame();
			} else {
				for (int x = 0; x < playButtonText.getWidth(); x++) {
					for (int y = 0; y < playButtonText.getHeight(); y++) {
						int alpha = playButtonText.getRGB(x, y) >> 24;
						playButtonText.setRGB(x, y, (alpha << 24) + textColorDefault);
					}
				}
			}
			if (input.mouseX > quitButtonX && input.mouseX < quitButtonX + quitButtonText.getWidth() &&
				input.mouseY > quitButtonY && input.mouseY < quitButtonY + quitButtonText.getHeight()) {
				for (int x = 0; x < quitButtonText.getWidth(); x++) {
					for (int y = 0; y < quitButtonText.getHeight(); y++) {
						int alpha = quitButtonText.getRGB(x, y) >> 24;
						quitButtonText.setRGB(x, y, (alpha << 24) + (textColorDefault - 0xFF));
					}
				}
				if (input.mousePressed)
					closeLauncher();
			} else {
				for (int x = 0; x < quitButtonText.getWidth(); x++) {
					for (int y = 0; y < quitButtonText.getHeight(); y++) {
						int alpha = quitButtonText.getRGB(x, y) >> 24;
						quitButtonText.setRGB(x, y, (alpha << 24) + textColorDefault);
					}
				}
			}
			//if(input.mousePressed) {
				frameLocation = frame.getLocationOnScreen();
				//System.out.println("DX: " + (input.mouseX - input.oldMouseX) + " DY: " + (input.mouseY - input.oldMouseY));
				System.out.println("X: " + input.mouseX + " OldX: " + input.oldMouseX);
				//System.out.println("Y: " + input.mouseY + " OldY: " + input.oldMouseY);
				//frame.setLocation(frameLocation.x + (input.mouseX - input.oldMouseX), frameLocation.y + (input.mouseY - input.oldMouseY));
			//}
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
		g.drawImage(quitButtonText, quitButtonX, quitButtonY, null);
		g.dispose();
		bs.show();
	}

	private void playGame() {
		if (musicPlaying)
			alSourceStop(menuMusicSource);
		alDeleteBuffers(menuMusicBuffer);
		alDeleteSources(menuMusicSource);
		AL.destroy();
		inputAllowed = false;
		frame.dispose();
		new Game();
	}

	private void closeLauncher() {
		if (musicPlaying)
			alSourceStop(menuMusicSource);
		alDeleteBuffers(menuMusicBuffer);
		alDeleteSources(menuMusicSource);
		AL.destroy();
		frame.dispose();
		System.exit(0);
	}

	public static void main(String[] args) {
		new Launcher();
	}
}
