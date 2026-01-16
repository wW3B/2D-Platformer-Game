package Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Scenes.HUD;
import Screen.Gameover;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;
import Sprite.Player;

public class LegendaryGem extends InteractiveTilesObject {
    @Override
	public void frontHit(Player player) {
		// TODO Auto-generated method stub
    	Gdx.app.log("Legendary", "Gem");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addScore(500);
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_coin.wav",Sound.class).play();
	}

	@Override
	public void backHit(Player player) {
		// TODO Auto-generated method stub
		Gdx.app.log("Legendary", "Gem");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addScore(500);
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_coin.wav",Sound.class).play();
	}

	private MyGame game;

    public LegendaryGem(World world, TiledMap map, Rectangle bounds) {
    	super(world,map,bounds);
        this.game = game;
        fixture.setUserData(this);
        setCategoryFilter(MyGame.COIN_BIT);
    }

    @Override
    public void footStep(Player player) {
    	Gdx.app.log("Legendary", "Gem");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addScore(500);
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_coin.wav",Sound.class).play();
    }

    @Override
    public void headHit(Player player) {
    	Gdx.app.log("Legendary", "Gem");
		 setCategoryFilter(MyGame.DESTROYED_BIT);
		 HUD.addScore(500);
		 getCell().setTile(null);
		 MyGame.manager.get("audio/sfx/smb_coin.wav",Sound.class).play();
    }
}
