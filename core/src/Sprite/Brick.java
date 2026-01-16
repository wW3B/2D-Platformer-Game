package Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Scenes.HUD;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;
import Sprite.Player;

public class Brick extends InteractiveTilesObject {
	
	@Override
	public void frontHit(Player player) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void backHit(Player player) {
		// TODO Auto-generated method stub
		
	}

	public Brick(World world, TiledMap map, Rectangle bounds) {
		 super(world,map,bounds);
		 fixture.setUserData(this);
		 setCategoryFilter(MyGame.BRICK_BIT);
		
	 }
	
	@Override
	public void headHit(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("Brick", "Collosion");
		setCategoryFilter(MyGame.DESTROYED_BIT);
		getCell().setTile(null);
		MyGame.manager.get("audio/sfx/smb_breakblock.wav",Sound.class).play();
	}

	@Override
	public void footStep(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("Brick", "Collosion");
		setCategoryFilter(MyGame.DESTROYED_BIT);
		getCell().setTile(null);
		MyGame.manager.get("audio/sfx/smb_breakblock.wav",Sound.class).play();
	}

	
}
