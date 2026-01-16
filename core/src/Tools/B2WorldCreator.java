package Tools;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;

import Screen.PlayScreen;
import Sprite.Brick;
import Sprite.Bridge;
import Sprite.Coins;
import Sprite.Finish;
import Sprite.Gem;
import Sprite.Heal;
import Sprite.LegendaryGem;
import Sprite.Over;
import Sprite.Spike;
import th.ac.swu.game.MyGame;

public class B2WorldCreator {
	public B2WorldCreator(World world, TiledMap map) {
		
		BodyDef bdef = new BodyDef();
        PolygonShape shape = new PolygonShape();
        FixtureDef fdef = new FixtureDef();
        Body body;

        // Create ground bodies fixture
        for (MapObject object : map.getLayers().get(3).getObjects().getByType(RectangleMapObject.class)) {
            Rectangle rect = ((RectangleMapObject) object).getRectangle();

            bdef.type = BodyDef.BodyType.StaticBody;
            bdef.position.set((rect.getX() + rect.getWidth() / 2) / MyGame.PPM, (rect.getY() + rect.getHeight() / 2) / MyGame.PPM);

            body = world.createBody(bdef);

            shape.setAsBox(rect.getWidth() / 2 / MyGame.PPM, rect.getHeight() / 2 / MyGame.PPM);
            fdef.shape = shape;
            body.createFixture(fdef);
        }
     // Create bridge bodies fixture
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
         	Rectangle rect = ((RectangleMapObject) object).getRectangle();

             new Bridge(world,map,rect);
         }

        // Create brick bodies fixture
       for (MapObject object : map.getLayers().get(5).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Brick(world,map,rect);
        }

        // Create coin bodies fixture
        for (MapObject object : map.getLayers().get(6).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Coins(world,map,rect);
        }
     // Create dead bodies fixture
        for (MapObject object : map.getLayers().get(9).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Over(world,map,rect);
        }
     // Create gem bodies fixture
        for (MapObject object : map.getLayers().get(7).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Gem(world,map,rect);
        }
     // Create legendary_gem bodies fixture
        for (MapObject object : map.getLayers().get(8).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new LegendaryGem(world,map,rect);
        }
        // Create bridge bodies fixture
        for (MapObject object : map.getLayers().get(4).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Bridge(world,map,rect);
        }
        // Create Spike bodies fixture
        for (MapObject object : map.getLayers().get(10).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Spike(world,map,rect);
        }
        // Create Finish bodies fixture
        for (MapObject object : map.getLayers().get(11).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Finish(world,map,rect);
        }
        for (MapObject object : map.getLayers().get(12).getObjects().getByType(RectangleMapObject.class)) {
        	Rectangle rect = ((RectangleMapObject) object).getRectangle();

            new Heal(world,map,rect);
        }
	}
}
