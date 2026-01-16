package Sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.World;

import Scenes.HUD;
import Screen.Gameover;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;
import Sprite.Player;

public class Finish extends InteractiveTilesObject {
    private MyGame game;

    public Finish(World world, TiledMap map, Rectangle bounds) {
        super(world, map, bounds);
        this.game = game;
        fixture.setUserData(this);
        setCategoryFilter(MyGame.DEAD_BIT);
    }

	@Override
    public void footStep(Player player) {
		Gdx.app.log("Finish", "Collision"); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        HUD.addScore(10000);
    }

    @Override
    public void headHit(Player player) {

    }

	@Override
	public void frontHit(Player player) {
        Gdx.app.log("Finish", "Collision"); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        HUD.addScore(10000);
	}

	@Override
	public void backHit(Player player) {
        Gdx.app.log("Finish", "Collision"); // Assuming setHp is a method in HUD to set the player's HP
        setCategoryFilter(MyGame.DESTROYED_BIT);
        HUD.addScore(10000);
	}
}
