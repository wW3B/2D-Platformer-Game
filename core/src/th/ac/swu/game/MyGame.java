package th.ac.swu.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import Screen.Controls;
import Screen.Gameover;
import Screen.MainMenu;
import Screen.PlayScreen;
import Screen.Win;
import Sprite.Over;

public class MyGame extends Game {
	public static final float V_WIDTH = 426.5f;
	public static final int V_HEIGHT = 240;
	public static final float PPM = 100;
	public SpriteBatch batch;
	
	public static final short GROUND_BIT = 1;
	public static final short PLAYER_BIT = 2;
	public static final short BRICK_BIT = 4;
	public static final short DEAD_BIT = 4;
	public static final short COIN_BIT = 8;
	public static final short DESTROYED_BIT = 16;
	public static final short OBJECT_BIT = 32;
	public static final short ENEMY_BIT = 64;
	public static AssetManager manager;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		manager = new AssetManager();
		manager.load("audio/music/menu-music.mp3",Music.class);
		manager.load("audio/sfx/confirm.mp3",Sound.class);
		manager.load("audio/sfx/smb_coin.wav",Sound.class);
		manager.load("audio/sfx/smb_jump-small.wav",Sound.class);
		manager.load("audio/sfx/smb_breakblock.wav",Sound.class);
		manager.load("audio/sfx/grass_walk.mp3",Sound.class);
		manager.load("audio/music/forest-am.mp3",Music.class);
		manager.load("audio/sfx/gameover.mp3",Sound.class);
		manager.load("audio/sfx/smb_powerup.wav",Sound.class);
		manager.load("audio/music/victory.mp3",Music.class);
		manager.load("audio/sfx/smb_bump.wav",Sound.class);
		manager.finishLoading();
		
		setScreen(new PlayScreen(this));
		
		
	}

	@Override
	public void render () {
		super.render();
		
		if (Gdx.input.isKeyPressed(Input.Keys.F12))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        if (Gdx.input.isKeyPressed(Input.Keys.F11))
            Gdx.graphics.setWindowedMode(1280, 720);
        manager.update();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		
	}
}
