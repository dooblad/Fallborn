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
	public static List<Integer> sounds = new ArrayList<Integer>();
	public static final String fireSpellSoundURL = "res/fireSpell.wav";
	
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
		
	}

	public static void loadSound(String URL) {
		WaveData waveData = WaveData.create(URL);
		IntBuffer buffer = BufferUtils.createIntBuffer(1);
		alGenBuffers(buffer);
		alBufferData(buffer.get(0), waveData.format, waveData.data, waveData.samplerate);
		waveData.dispose();
		
	}

	public static void playSound(int soundBuffer) {

	}

	public static void stopSound(int soundBuffer) {

	}
}
