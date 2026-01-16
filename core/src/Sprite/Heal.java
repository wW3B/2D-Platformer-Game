package Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Scenes.HUD;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;

public class Heal extends InteractiveTilesObject {
	
	@Override
	public void frontHit(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("HP", "Collosion");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addHealth(1); // This will increase HP by 1 and update the heart images
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_powerup.wav",Sound.class).play();
	}

	@Override
	public void backHit(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("HP", "Collosion");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addHealth(1); // This will increase HP by 1 and update the heart images
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_powerup.wav",Sound.class).play();
	}

	public Heal(World world, TiledMap map, Rectangle bounds) {
		 super(world,map,bounds);
		 fixture.setUserData(this);
		 setCategoryFilter(MyGame.COIN_BIT);
	 }
	
	 @Override
	public void headHit(Player player) {
		// TODO Auto-generated method stub
			Gdx.app.log("HP", "Collosion");
			 setCategoryFilter(MyGame.DESTROYED_BIT);
			 HUD.addHealth(1); // This will increase HP by 1 and update the heart images
			 getCell().setTile(null);
			 MyGame.manager.get("audio/sfx/smb_powerup.wav",Sound.class).play();
		}

	@Override
	public void footStep(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("HP", "Collosion");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addHealth(1); // This will increase HP by 1 and update the heart images
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_powerup.wav",Sound.class).play();
	}

}
