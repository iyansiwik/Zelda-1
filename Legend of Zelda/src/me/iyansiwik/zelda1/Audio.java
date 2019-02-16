package me.iyansiwik.zelda1;

import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class Audio {
	
	private Clip clip;

	private String path;
	
	private float volume;
	private boolean loop;

	public Audio(String path, float volume, boolean loop) {
		if(volume < 0f) volume = 0f;
		if(volume > 2f) volume = 2f;
		this.volume = volume;
		this.loop = loop;
		this.path = path;
	}
	
	public Audio(String path, float volume) {
		this(path, volume, false);
	}
	
	public Audio(String path, boolean loop) {
		this(path, 1f, loop);
	}
	
	public Audio(String path) {
		this(path, 1f, false);
	}
	
	public void play() {
		try {
			AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File("res/"+path+".wav"));
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
			gainControl.setValue(20f * (float)Math.log10(volume));
			if(loop) clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch(LineUnavailableException | IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}
	
	public float getVolume() {
		return volume;
	}
	
	public void stop() {
		if(clip != null) clip.stop();
	}
}
