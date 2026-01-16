package Sprite;

import com.badlogic.gdx.maps.tiled.TiledMap;
import Sprite.Player;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Filter;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Screen.Gameover;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;

public abstract class InteractiveTilesObject {
	protected World world;
	protected TiledMap map;
	protected TiledMapTile tile;
	protected Rectangle bounds;
	protected Body body;
	protected Fixture fixture;
	
	public InteractiveTilesObject(World world, TiledMap map, Rectangle bounds) {
		this.world = world;
		this.map = map;
		this.bounds = bounds;
		
		BodyDef bdef = new BodyDef();
		FixtureDef fdef = new FixtureDef();
		PolygonShape shape = new PolygonShape();
		 
		bdef.type = BodyDef.BodyType.StaticBody;
        bdef.position.set((bounds.getX() + bounds.getWidth() / 2) / MyGame.PPM, (bounds.getY() + bounds.getHeight() / 2) / MyGame.PPM);

        body = world.createBody(bdef);

        shape.setAsBox(bounds.getWidth() / 2 / MyGame.PPM, bounds.getHeight() / 2 / MyGame.PPM);
        fdef.shape = shape;
        fixture = body.createFixture(fdef);
	}
	
	public abstract void footStep(Player player);
	public abstract void headHit(Player player);
	public abstract void frontHit(Player player);
	public abstract void backHit(Player player);
	public void setCategoryFilter(short filterBit) {
		Filter filter = new Filter();
		filter.categoryBits = filterBit;
		fixture.setFilterData(filter);
	}
	  public TiledMapTileLayer.Cell getCell(){
	        TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get(2);
	        return layer.getCell((int)(body.getPosition().x * MyGame.PPM / 16),
	                (int)(body.getPosition().y * MyGame.PPM / 16));
	    }

}