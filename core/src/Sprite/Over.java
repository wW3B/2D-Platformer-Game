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

public class Over extends InteractiveTilesObject {
    private MyGame game;

    public Over(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        this.game = game;
        fixture.setUserData(this);
        setCategoryFilter(MyGame.DEAD_BIT);
    }

	@Override
    public void footStep(Player player) {
        Gdx.app.log("Dead", "Collision");
        HUD.decreaseHp(2); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        MyGame.manager.get("audio/sfx/smb_bump.wav",Sound.class).play();
    }

    @Override
    public void headHit(Player player) {
        Gdx.app.log("Dead", "Collision");
        HUD.decreaseHp(2); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        MyGame.manager.get("audio/sfx/smb_bump.wav",Sound.class).play();
    }

	@Override
	public void frontHit(Player player) {
        Gdx.app.log("Dead", "Collision");
        HUD.decreaseHp(2); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        MyGame.manager.get("audio/sfx/smb_bump.wav",Sound.class).play();
	}

	@Override
	public void backHit(Player player) {
        Gdx.app.log("Dead", "Collision");
        HUD.decreaseHp(2); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        MyGame.manager.get("audio/sfx/smb_bump.wav",Sound.class).play();
	}
}
