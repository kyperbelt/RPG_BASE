package com.rpg.game.utils;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.ObjectMap;
import com.rpg.game.assets.GameAssets;

public class SoundManager {
	
	private float master_volume;
	private float music_volume;
	private float sound_volume;
	private float voice_volume;
	
	private GameAssets assets;
	private ObjectMap<String,String> names;
	
	private Music current_music;
	
	public SoundManager(GameAssets assets){
		this.assets = assets;
		names = new ObjectMap<String, String>();
		master_volume = 1f;
		music_volume = 1f;
		sound_volume = 1f;
		voice_volume = 1f;
	}
	/**
	 * get the currently playing music
	 * null if none lul
	 * @return
	 */
	public Music getCurrentMusic(){
		return current_music;
	}
	/**
	 * play the Sound Object with the given name
	 * - this will play the sound using the (master*sound) volume 
	 * @param name
	 */
	public void playSound(String name){
		if(!containsName(name))
			return;
		Sound s = assets.getSound(names.get(name));
		if(s == null){
			RpgUtils.logError("Sound is not loaded +["+name+"@"+names.get(name)+"] could not play.");
			return;
		}
		s.play(master_volume*sound_volume);
	}
	/**
	 * play the Music object with the given name
	 * will play using the (master*music) volume
	 * automatically loops this music
	 * @param name
	 */
	public void playMusic(String name){
		playMusic(name,true);
	}
	/**
	 * play the Music Object with the given name
	 * will play using the (master*music) volume
	 * set looped to false to only play once
	 * @param name
	 * @param looped
	 */
	public void playMusic(String name,boolean looped){
		if(!containsName(name))
			return;
		Music m = assets.getMusic(names.get(name));
		if(m == null){
			RpgUtils.logError("Music is not loaded +["+name+"@"+names.get(name)+"] could not play.");
			return;
		}
		if(current_music!=null&&current_music.isPlaying())
			current_music.stop();
		current_music = m;
		current_music.setVolume(master_volume*music_volume);
		current_music.setLooping(looped);
		current_music.play();
	}
	/**
	 * play the Sound Object with the given name
	 * will play using the (master*voice) volume
	 * @param name
	 */
	public void playVoice(String name){
		if(!containsName(name))
			return;
		Sound s = assets.getSound(names.get(name));
		if(s == null){
			RpgUtils.logError("Sound is not loaded +["+name+"@"+names.get(name)+"] could not play.");
			return;
		}
		s.play(master_volume*voice_volume);
	}
	/**
	 * load a Music.class object using the default 
	 * GameAssets manager
	 * @param name - the short name you would like to use to call for this music
	 * @param path - the path to use to load the file (ex. sound/badassmusic.ogg)
	 */
	public void loadMusic(String name,String path){
		if(names.containsKey(name)&&assets.itemLoaded(name)){
			RpgUtils.logError("failed to load music object with duplicate name ["+name+"] try again.");
			return;
		}
		assets.loadMusic(path);
		names.put(name, path);
	}
	
	private boolean containsName(String name){
		if(!names.containsKey(name)){
			RpgUtils.logError("SoundManager could not find ["+name+"]. Make sure it was loaded and "
					+ "that you used the correct spelling.");
			return false;
		}else{
			return true;
		}
	}
	
	/**
	 * load a Sound.class object using the default
	 * GameAssets manager
	 * @param name -the short name used to call this object
	 * @param path - path used to load the file
	 */
	public void loadSound(String name,String path){
		if(names.containsKey(name)&&assets.itemLoaded(name)){
			RpgUtils.logError("failed to load sound object with duplicate name ["+name+"] try again.");
			return;
		}
		assets.loadSound(path);
		names.put(name, path);
	}
	/**
	 * set the master volume (0-1f) all other volume is multiplied by this
	 * @param master
	 */
	public void setMasterVolume(float master){
		master_volume = RpgUtils.clampNum(master, 0, 1f);
	}
	
	public float getMasterVolume(){
		return master_volume;
	}
	/**
	 * set music volume 0-1f
	 */
	public void setMusicVolume(float music){
		music_volume = RpgUtils.clampNum(music, 0, 1f);
	}
	
	public float getMusicVolume(){
		return music_volume;
	}
	/**
	 * set sound volume 0-1f
	 * @param sound
	 */
	public void setSoundVolume(float sound){
		sound_volume = RpgUtils.clampNum(sound, 0, 1f);
	}
	
	public float getSoundVolume(){
		return sound_volume;
	}
	/**
	 * set voice volume 0-1f
	 * @param voice
	 */
	public void setVoiceVolume(float voice){
		voice_volume = RpgUtils.clampNum(voice, 0, 1f);
	}
	
	public float getVoiceVolume(){
		return voice_volume;
	}

}
