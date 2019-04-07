package ca.mcgill.ecse223.block.view;

import java.util.ArrayList;
import java.util.Collections;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public class MusicShuffler {

	private static ArrayList<Media> gamePlayedMusic = new ArrayList<>();
	private static Media selectMusic;
	//private static ArrayList<Media> adminMusic = new ArrayList<>();
	private static MediaPlayer mp;
	
	public MusicShuffler() {
		//populate the plaulists
//		gamePlayedMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/EDM1.wav")));
//		gamePlayedMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/DL.mp3")));
//		gamePlayedMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/Chill.wav")));
//		gamePlayedMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/PimPoy.wav")));
		gamePlayedMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/XYOU.mp3")));
		//adminMusic.add(new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/Intro.mp3")));
		selectMusic=new Media(Block223Page.getResource("ca/mcgill/ecse223/block/view/resources/Space"));
	}
	
	public static void playNextPlayedMusic() {
		if (mp!=null) {
		mp.stop();
		mp.dispose();}
		Collections.shuffle(gamePlayedMusic);
		mp = new MediaPlayer(gamePlayedMusic.get(0));
		mp.setVolume(0.75);
		mp.setCycleCount(Integer.MAX_VALUE);
		mp.play();
//		mp.setOnEndOfMedia(new Runnable(){
//			@Override
//			public void run() {
//			playNextPlayedMusic();}
//		});
	}
//	public static void playAdminMusic() {
//		if (mp!=null) {
//		mp.stop();
//		mp.dispose();}
//		mp = new MediaPlayer(gamePlayedMusic.get(0));
//		mp.setCycleCount(Integer.MAX_VALUE);
//		mp.play();
//	}
	public static void playSelectMusic() {
		if (mp!=null) {
		mp.stop();
		mp.dispose();}
		mp = new MediaPlayer(selectMusic);
		mp.setVolume(0.75);
		mp.setCycleCount(Integer.MAX_VALUE);
		mp.play();
	}
	public static void pauseMusic() {
		mp.pause();
	}
	public static void resumeMusic() {
		mp.play();
	}
	public static boolean isPlaying() {
		 return mp.getStatus()==MediaPlayer.Status.PLAYING;
	}
	public static boolean isMuted() {
		return mp.isMute();
	}

	public static void unmuteMusic() {
		mp.setMute(false);
		
	}

	public static void muteMusic() {
		mp.setMute(true);
		
	}
}
