package Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import th.ac.swu.game.MyGame;

public class Gameover implements Screen {

    private MyGame game;
    private Stage stage;

    private static final int BUTTON_WIDTH = 125; // New button width
    private static final int BUTTON_HEIGHT = 75; // New button height

    Texture background;
    Texture anykeys;
    boolean showStart;
    float timer;
    private Texture exitTextureIdle;
    private Texture exitTextureHover;

    private ImageButton exitButton;
    
    private Sound slash;
    private Sound confirm;

    public Gameover(MyGame game) {
        this.game = game;
        background = new Texture("gameover-bg.jpg");
        anykeys = new Texture("btn/enter_key.png");
        
        Sprite bgSprite = new Sprite(background);
        bgSprite.setSize(MyGame.V_WIDTH, MyGame.V_HEIGHT); // Corrected the size setting

        Sprite keySprite = new Sprite(anykeys);
        keySprite.setSize(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM); // Corrected the size setting

        timer = 0;
        
        slash = MyGame.manager.get("audio/sfx/gameover.mp3", Sound.class);
        slash.play();
    }

    @Override
    public void show() {
        stage = new Stage(new FitViewport(MyGame.V_WIDTH / MyGame.PPM, MyGame.V_HEIGHT / MyGame.PPM)); // Corrected the viewport settings
        Gdx.input.setInputProcessor(stage);

     // Initialize the stage for the UI
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures for the button
        Texture exitTextureIdle = new Texture(Gdx.files.internal("btn/exit_idle.png"));
        Texture exitTextureHover = new Texture(Gdx.files.internal("btn/exit_press.png"));

        // Create button style
        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = new TextureRegionDrawable(exitTextureIdle);
        exitButtonStyle.imageOver = new TextureRegionDrawable(exitTextureHover);

        // Create and configure the exit button
        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(50, 50);
        exitButton.setPosition(10, 10);

        // Add listener to the button
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                Gdx.app.exit();
                return true;
            }
        });

        // Add the button to the stage
        stage.addActor(exitButton);
    
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        timer += delta;
        if (timer >= 1f) {
            showStart = !showStart;
            timer = 0;
        }

        game.batch.begin();
        game.batch.draw(background, 0, 0, MyGame.V_WIDTH, MyGame.V_HEIGHT); // Corrected the draw call

        // Render blinking text
        if (showStart) {
            game.batch.draw(anykeys, 170, 15, 90, 40); // Adjusted the position and size
        }

        game.batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.F12))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        if (Gdx.input.isKeyPressed(Input.Keys.F11))
            Gdx.graphics.setWindowedMode(426, 240);

        // Check if any key is pressed to start the game, except function keys
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            confirm = MyGame.manager.get("audio/sfx/confirm.mp3", Sound.class);
            confirm.play();
            game.setScreen(new PlayScreen(game));
        }
    }

    

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        // Nothing to do here
    }

    @Override
    public void resume() {
        // Nothing to do here
    }

    @Override
    public void hide() {
        // Nothing to do here
    }

    @Override
    public void dispose() {
        background.dispose();
        anykeys.dispose();
        exitTextureIdle.dispose();
        exitTextureHover.dispose();
        stage.dispose();
    }
}
