package Screen;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;

import th.ac.swu.game.MyGame;

public class MainMenu implements Screen {

    private MyGame game;
    private Stage stage;

    private static final int BUTTON_WIDTH = 125; // New button width
    private static final int BUTTON_HEIGHT = 75; // New button height

    Texture background;
    Texture logo;
    Texture anykeys;
    boolean showStart;
    float timer;
    private Texture exitTextureIdle;
    private Texture exitTextureHover;

    private ImageButton exitButton;
    private ImageButton musicButton;

    private boolean isMusicOn = true;
    
    private Music musicmenu;
    private Sound confirm;

    public MainMenu(MyGame game) {
        this.game = game;
        logo = new Texture("Logo.png");
        background = new Texture("mont-bg.jpg");
        anykeys = new Texture("btn/anykeys.png");
        timer = 0;
        
        musicmenu = MyGame.manager.get("audio/music/menu-music.mp3",Music.class);
        musicmenu.setLooping(true);
        musicmenu.play();
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        // Load textures
        exitTextureIdle = new Texture(Gdx.files.internal("btn/exit_idle.png"));
        exitTextureHover = new Texture(Gdx.files.internal("btn/exit_press.png"));

        // Create button styles
        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = new TextureRegionDrawable(exitTextureIdle);
        exitButtonStyle.imageOver = new TextureRegionDrawable(exitTextureHover);
        Texture musicOnIdleTexture = new Texture(Gdx.files.internal("btn/volume-on-idle.png"));
        Texture musicOnHoverTexture = new Texture(Gdx.files.internal("btn/volume-on-hover.png"));
        Texture musicOffIdleTexture = new Texture(Gdx.files.internal("btn/volume-off-idle.png"));
        Texture musicOffHoverTexture = new Texture(Gdx.files.internal("btn/volume-off-hover.png"));

        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setPosition(0,10);

        // Add listener to button
        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int button) {
                // Exit the game
                Gdx.app.exit();
                return true;
            }
        });

        // Add button to stage
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
        button.setSize(90, 70);

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
        	musicmenu.play();
        } else {
        	musicmenu.pause();
        }

        // Update the button on the stage
        stage.getActors().removeValue(musicButton, true);
        musicButton = createMusicButton(isMusicOn, musicOnIdleTexture, musicOnHoverTexture, musicOffIdleTexture, musicOffHoverTexture);
        musicButton.setPosition(Gdx.graphics.getWidth() - musicButton.getWidth() - 10, Gdx.graphics.getHeight() - musicButton.getHeight() - 10); // Adjust position as needed
        stage.addActor(musicButton);
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
        game.batch.draw(background, 0, 0);
        game.batch.draw(logo,30 ,680, 585, 410);

        // Render blinking text
        if (showStart) {
            game.batch.draw(anykeys, 800,20, 300, 90);
        }

        game.batch.end();

        stage.act(delta);
        stage.draw();

        if (Gdx.input.isKeyPressed(Input.Keys.F12))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        if (Gdx.input.isKeyPressed(Input.Keys.F11))
            Gdx.graphics.setWindowedMode(1280, 720);

        // Check if any key is pressed to start the game, except function keys
        if (isNonFunctionKeyPressed()) {
        	confirm = MyGame.manager.get("audio/sfx/confirm.mp3",Sound.class);
        	confirm.play();
        	game.setScreen(new Controls(game));
        }
    }

    private boolean isNonFunctionKeyPressed() {
        for (int key = Input.Keys.NUM_0; key <= Input.Keys.Z; key++) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        for (int key = Input.Keys.ANY_KEY; key < Input.Keys.F1; key++) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        for (int key = Input.Keys.F12 + 1; key <= Input.Keys.SYM; key++) {
            if (Gdx.input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
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
        logo.dispose();
        anykeys.dispose();
        exitTextureIdle.dispose();
        exitTextureHover.dispose();
        stage.dispose();
    }
}
