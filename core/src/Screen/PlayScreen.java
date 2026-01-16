package Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import Scenes.HUD;
import Sprite.Player;
import Tools.B2WorldCreator;
import Tools.WorldContactListener;
import th.ac.swu.game.MyGame;

public class PlayScreen implements Screen {

    private MyGame game;
    private OrthographicCamera gamecam;
    private Viewport gamePort;
    private HUD hud;
    private TextureAtlas atlas;

    private TmxMapLoader maploader;
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    // Box2d
    private World world;
    private Box2DDebugRenderer b2dr;

    private Player player;

    // Stage for UI
    private Stage stage;
    private ImageButton exitButton;
    private ImageButton musicButton;

    private Music musicam;
    private Sound jump;
    private boolean isMusicOn = true;

    public PlayScreen(MyGame game) {
        atlas = new TextureAtlas("Player.pack");

        this.game = game;
        gamecam = new OrthographicCamera();
        gamePort = new StretchViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM, gamecam);

        hud = new HUD(game.batch);

        maploader = new TmxMapLoader();
        map = maploader.load("levels/level1.tmx");
        renderer = new OrthogonalTiledMapRenderer(map, 1 / MyGame.PPM);
        gamecam.position.set(gamePort.getWorldWidth() / 2, gamePort.getWorldHeight() / 2, 0);

        world = new World(new Vector2(0, -10), true);
        b2dr = new Box2DDebugRenderer();

        new B2WorldCreator(world,map);

        // Initialize player
        player = new Player(world,this);

        world.setContactListener(new WorldContactListener());
        
        // Set the HP listener to transition to Gameover screen
        HUD.setHPListener(new HUD.HPListener() {
            @Override
            public void onHPZero() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new Gameover(game));
                    }
                });
            }
            
        });
        
        // Set the Score listener to transition to Win screen
        HUD.setWINListener(new HUD.WINListener() {
            @Override
            public void onWIN() {
                Gdx.app.postRunnable(new Runnable() {
                    @Override
                    public void run() {
                        game.setScreen(new Win(game));
                    }
                });
            }
        });

        musicam = MyGame.manager.get("audio/music/forest-am.mp3", Music.class);
        musicam.setLooping(true);
        musicam.play();

        // Initialize the stage for the UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures for the buttons
        Texture exitTextureIdle = new Texture(Gdx.files.internal("btn/exit_idle.png"));
        Texture exitTextureHover = new Texture(Gdx.files.internal("btn/exit_press.png"));
        Texture musicOnIdleTexture = new Texture(Gdx.files.internal("btn/volume-on-idle.png"));
        Texture musicOnHoverTexture = new Texture(Gdx.files.internal("btn/volume-on-hover.png"));
        Texture musicOffIdleTexture = new Texture(Gdx.files.internal("btn/volume-off-idle.png"));
        Texture musicOffHoverTexture = new Texture(Gdx.files.internal("btn/volume-off-hover.png"));

        // Create button style for exit button
        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = new TextureRegionDrawable(exitTextureIdle);
        exitButtonStyle.imageOver = new TextureRegionDrawable(exitTextureHover);

        // Create and configure the exit button
        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(50, 50);
        exitButton.setPosition(10, 10);

        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        // Add the exit button to the stage
        stage.addActor(exitButton);

        // Initial music button setup
        musicButton = createMusicButton(isMusicOn, musicOnIdleTexture, musicOnHoverTexture, musicOffIdleTexture, musicOffHoverTexture);
        musicButton.setSize(70, 70);
        musicButton.setPosition(Gdx.graphics.getWidth() - musicButton.getWidth() - 10, Gdx.graphics.getHeight() - musicButton.getHeight() - 10);

        // Add the music button to the stage
        stage.addActor(musicButton);
    }

    private ImageButton createMusicButton(boolean isMusicOn, Texture musicOnIdleTexture, Texture musicOnHoverTexture, Texture musicOffIdleTexture, Texture musicOffHoverTexture) {
        ImageButton.ImageButtonStyle musicButtonStyle = new ImageButton.ImageButtonStyle();
        if (isMusicOn) {
            musicButtonStyle.imageUp = new TextureRegionDrawable(musicOnIdleTexture);
            musicButtonStyle.imageOver = new TextureRegionDrawable(musicOnHoverTexture);
        } else {
            musicButtonStyle.imageUp = new TextureRegionDrawable(musicOffIdleTexture);
            musicButtonStyle.imageOver = new TextureRegionDrawable(musicOffHoverTexture);
        }

        ImageButton button = new ImageButton(musicButtonStyle);
        button.setSize(70, 70);

        button.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                toggleMusic(musicOnIdleTexture, musicOnHoverTexture, musicOffIdleTexture, musicOffHoverTexture);
                return true;
            }
        });

        return button;
    }

    private void toggleMusic(Texture musicOnIdleTexture, Texture musicOnHoverTexture, Texture musicOffIdleTexture, Texture musicOffHoverTexture) {
        isMusicOn = !isMusicOn;
        if (isMusicOn) {
            musicam.play();
        } else {
            musicam.pause();
        }

        // Update the button on the stage
        stage.getActors().removeValue(musicButton, true);
        musicButton = createMusicButton(isMusicOn, musicOnIdleTexture, musicOnHoverTexture, musicOffIdleTexture, musicOffHoverTexture);
        musicButton.setPosition(Gdx.graphics.getWidth() - musicButton.getWidth() - 10, Gdx.graphics.getHeight() - musicButton.getHeight() - 10); // Adjust position as needed
        stage.addActor(musicButton);
    }

    public TextureAtlas getAtlas() {
        return atlas;
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    public void handleInput(float dt) {
        if (player.currentState != player.currentState.DEAD) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
                if (player.jumpCount <= 1) {
                    player.b2body.applyLinearImpulse(new Vector2(0, 3f), player.b2body.getWorldCenter(), true);
                    jump = MyGame.manager.get("audio/sfx/smb_jump-small.wav", Sound.class);
                    jump.play();
                    player.jumpCount++;
                }
            }
            if (Gdx.input.isKeyPressed(Input.Keys.D) && player.b2body.getLinearVelocity().x <= 1.25) {
                player.b2body.applyLinearImpulse(new Vector2(0.1f, 0), player.b2body.getWorldCenter(), true);
            }
            if (Gdx.input.isKeyPressed(Input.Keys.A) && player.b2body.getLinearVelocity().x >= -1.25) {
                player.b2body.applyLinearImpulse(new Vector2(-0.1f, 0), player.b2body.getWorldCenter(), true);
            }
        }
    }

    private void checkIfPlayerOnGround() {
        // This method should check if the player is on the ground
        if (player.b2body.getPosition().y <= 0) { // groundLevel should be set appropriately
            player.jumpCount = 0;
        }
    }


    public void update(float dt) {
        handleInput(dt);
        checkIfPlayerOnGround();
        
        world.step(1 / 60f, 6, 2);
        hud.update(dt);

        gamecam.position.x = player.b2body.getPosition().x;

        gamecam.update();
        renderer.setView(gamecam);

        player.update(dt);
    }

    @Override
    public void render(float delta) {
        update(delta);

        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.render();

        //b2dr.render(world, gamecam.combined);

        game.batch.setProjectionMatrix(gamecam.combined);
        game.batch.begin();
        player.draw(game.batch);
        game.batch.end();
        game.batch.setProjectionMatrix(hud.stage.getCamera().combined);
        hud.stage.draw();

        // Draw the stage for UI elements
        stage.act(delta);
        stage.draw();
        
        
    }
    

    @Override
    public void resize(int width, int height) {
        gamePort.update(width, height);
        stage.getViewport().update(width, height, true);
    }
    
   
    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        world.dispose();
        b2dr.dispose();
        stage.dispose();
        hud.dispose();
    
    }
}
