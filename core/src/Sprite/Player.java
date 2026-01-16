package Sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;

import Scenes.HUD;
import Screen.PlayScreen;
import th.ac.swu.game.MyGame;

public class Player extends Sprite {
    public enum State {FALLING, JUMPING, IDLE, RUNNING,DEAD}
    public State currentState;
    public State previousState;
    public World world;
    public Body b2body;
    public int jumpCount = 0;

    
    private TextureRegion playerJump;
    private TextureRegion playerFall;
    private TextureRegion playerDead;
    private Animation<TextureRegion> playerRun;
    private Animation<TextureRegion> playerIdle;
    
    private float stateTimer;
    private boolean runningRight;
    

    

    
    public Player(World world, PlayScreen screen) {
        super(screen.getAtlas().findRegion("Idle"));
        this.world = world;
        currentState = State.IDLE;
        previousState = State.IDLE;
        stateTimer = 0;
        runningRight = true;

        Array<TextureRegion> frames = new Array<TextureRegion>();
       
        // Load running animation frames
        for (int i= 0; i<11; i++)
            frames.add(new TextureRegion(getTexture(), i * 32, 70, 29, 35));
        playerRun = new Animation<>(0.1f, frames);
        playerRun.setPlayMode(Animation.PlayMode.LOOP);
        frames.clear();

        // Load idle animation frames
        for (int i= 0; i<11; i++)
            frames.add(new TextureRegion(getTexture(), i * 32, 35, 29, 35));
        playerIdle = new Animation<>(0.2f, frames);
        playerIdle.setPlayMode(Animation.PlayMode.LOOP);
        setBounds(0, 35, 16 / MyGame.PPM, 16 / MyGame.PPM);
        setRegion(playerIdle.getKeyFrame(0));

        // Load jumping frame
        playerJump = new TextureRegion(getTexture(), 324, 70, 27, 35);
        
        playerFall = new TextureRegion(getTexture(), 297, 35, 27, 35);
        
        playerDead = new TextureRegion(getTexture(), 0, 0, 29, 35);

        definePlayer();
        
    }

    public void update(float dt) {
    	checkIfPlayerOnGround();
        setPosition(b2body.getPosition().x - getWidth() / 2, b2body.getPosition().y - getHeight() / 2);
        setRegion(getFrame(dt));
    }

    private void checkIfPlayerOnGround() {
		// TODO Auto-generated method stub
    	if (currentState != State.JUMPING) { // groundLevel should be set appropriately
    		jumpCount = 0;
        }
	}

	public TextureRegion getFrame(float dt) {
        currentState = getState();
        TextureRegion region;

        switch (currentState) {
        	case DEAD:
        		region = playerDead;
        		break;
            case JUMPING:
                region = playerJump;
                break;
            case RUNNING:
                region = playerRun.getKeyFrame(stateTimer, true);
                break;
            case FALLING:
            	region = playerFall;
            case IDLE:
            default:
                region = playerIdle.getKeyFrame(stateTimer,true);
                break;
        }

        if ((b2body.getLinearVelocity().x < 0 || !runningRight) && !region.isFlipX()) {
            region.flip(true, false);
            runningRight = false;
        } else if ((b2body.getLinearVelocity().x > 0 || runningRight) && region.isFlipX()) {
            region.flip(true, false);
            runningRight = true;
        }

        stateTimer = currentState == previousState ? stateTimer + dt : 0;
        previousState = currentState;
        return region;
    }

    public State getState() {
        if (b2body.getLinearVelocity().y > 0 || (b2body.getLinearVelocity().y < 0 && previousState == State.JUMPING))
            return State.JUMPING;
        else if (b2body.getLinearVelocity().y < 0)
            return State.FALLING;
        else if (b2body.getLinearVelocity().x != 0)
            return State.RUNNING;
        else
            return State.IDLE;
    }

    public void definePlayer() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(215 / MyGame.PPM, 50 / MyGame.PPM);
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(5 / MyGame.PPM);
        fdef.filter.categoryBits = MyGame.PLAYER_BIT;
        fdef.filter.maskBits = MyGame.GROUND_BIT | MyGame.COIN_BIT | MyGame.BRICK_BIT;

        fdef.shape = shape;
        b2body.createFixture(fdef);

        
        
        EdgeShape foot = new EdgeShape();
        foot.set(new Vector2(-2 / MyGame.PPM,-7 / MyGame.PPM), new Vector2(2/ MyGame.PPM,-7 / MyGame.PPM));
        fdef.shape = foot;
        fdef.isSensor = true;
        
        b2body.createFixture(fdef).setUserData("foot");
        
       EdgeShape head = new EdgeShape();
        head.set(new Vector2(-2 / MyGame.PPM,6 / MyGame.PPM), new Vector2(2/ MyGame.PPM,6 / MyGame.PPM));
        fdef.shape = head;
        fdef.isSensor = true;
        
        b2body.createFixture(fdef).setUserData("head");
        
        // Define vertical front sensor
        EdgeShape front = new EdgeShape();
        front.set(new Vector2(7 / MyGame.PPM, -2 / MyGame.PPM), new Vector2(7 / MyGame.PPM, 2 / MyGame.PPM));
        fdef.shape = front;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("front");

        // Define vertical back sensor
        EdgeShape back = new EdgeShape();
        back.set(new Vector2(-7 / MyGame.PPM, -2 / MyGame.PPM), new Vector2(-7 / MyGame.PPM, 2 / MyGame.PPM));
        fdef.shape = back;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData("back");
        
        shape.dispose();
    }
}
