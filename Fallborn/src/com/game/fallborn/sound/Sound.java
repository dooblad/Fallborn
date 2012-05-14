package com.game.fallborn.sound;

import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.openal.*;
import static org.lwjgl.openal.AL10.*;
import org.lwjgl.util.WaveData;

public class Sound {
	private static List<IntBuffer> buffers = new ArrayList<IntBuffer>();
	private static List<IntBuffer> sources = new ArrayList<IntBuffer>();
	public static final IntBuffer fireSpell = loadSound("res/fireSpell.wav");
	
	public static void initializeSound() {
		try {
			AL.create();
		} catch(LWJGLException e) {
			e.printStackTrace();
			System.err.println("Could not create an OpenAL context");
			System.exit(1);
		}
		
		
	}
	
	public static void destroySound() {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		for(int i = 0; i < buffers.size(); i++) {
			alDeleteBuffers(buffers.get(i));
			buffers.remove(i);
		}
		for(int i = 0; i < sources.size(); i++) {
			alDeleteSources(sources.get(i));
			sources.remove(i);
		}
		AL.destroy();
	}

	public static IntBuffer loadSound(String URL) {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		WaveData waveData = WaveData.create(URL);
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		alGenBuffers(buffer);
		alBufferData(buffer.get(0), waveData.format, waveData.data, waveData.samplerate);
		waveData.dispose();
		IntBuffer source = BufferUtils.createIntBuffer(1);
		alGenSources(source);
		alSourcei(source.get(0), AL_BUFFER, buffer.get(0));
		buffers.add(buffer);
		sources.add(source);
		return source;
	}

	public static void playSound(IntBuffer soundSource) {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		alSourcePlay(soundSource);
	}

	public static void stopSound(IntBuffer soundSource) {
		try {
			AL.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
		alSourceStop(soundSource);
	}
}
