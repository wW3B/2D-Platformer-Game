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
import Screen.MainMenu;

public class Controls implements Screen {

    private MyGame game;
    private Stage stage;

    private static final int BUTTON_WIDTH = 125; // New button width
    private static final int BUTTON_HEIGHT = 75; // New button height

    Texture background;
    Texture enter_key;
    float timer;
    boolean showStart;

    private Texture exitTextureIdle;
    private Texture exitTextureHover;

    private ImageButton exitButton;

    private Texture w_btn_idle, w_btn_press;
    private Texture a_btn_idle, a_btn_press;
    private Texture s_btn_idle, s_btn_press;
    private Texture d_btn_idle, d_btn_press;
    private Texture space_idle, space_press;
    
    private Music musicmenu;
    private Sound confirm;

    private CustomButton wButton, aButton, sButton, dButton,spaceButton;

    public Controls(MyGame game) {
        this.game = game;
        background = new Texture("control-bg.jpg");
        enter_key = new Texture("btn/enter.png");

        w_btn_idle = new Texture("btn/w_btn_idle.png");
        w_btn_press = new Texture("btn/w_btn_press.png");

        a_btn_idle = new Texture("btn/a_btn_idle.png");
        a_btn_press = new Texture("btn/a_btn_press.png");

        s_btn_idle = new Texture("btn/s_btn_idle.png");
        s_btn_press = new Texture("btn/s_btn_press.png");

        d_btn_idle = new Texture("btn/d_btn_idle.png");
        d_btn_press = new Texture("btn/d_btn_press.png");
        
        space_idle = new Texture("btn/space_btn_idle.png");
        space_press = new Texture("btn/space_btn_press.png");

        timer = 0;
    }

    @Override
    public void show() {
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);

        exitTextureIdle = new Texture(Gdx.files.internal("btn/exit_idle.png"));
        exitTextureHover = new Texture(Gdx.files.internal("btn/exit_press.png"));

        ImageButton.ImageButtonStyle exitButtonStyle = new ImageButton.ImageButtonStyle();
        exitButtonStyle.imageUp = new TextureRegionDrawable(exitTextureIdle);
        exitButtonStyle.imageOver = new TextureRegionDrawable(exitTextureHover);

        exitButton = new ImageButton(exitButtonStyle);
        exitButton.setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButton.setPosition(0, 10);

        exitButton.addListener(new InputListener() {
            @Override
            public boolean touchDown(InputEvent event, float x, float y, int pointer, int buttonId) {
                Gdx.app.exit();
                return true;
            }
        });

        stage.addActor(exitButton);

        // Create buttons
        wButton = createCustomButton(w_btn_idle, w_btn_press, 380, Gdx.graphics.getHeight() / 2 + BUTTON_HEIGHT, Input.Keys.W);
        aButton = createCustomButton(a_btn_idle, a_btn_press, 300, Gdx.graphics.getHeight() / 2, Input.Keys.A);
        sButton = createCustomButton(s_btn_idle, s_btn_press, 380, Gdx.graphics.getHeight() / 2, Input.Keys.S);
        dButton = createCustomButton(d_btn_idle, d_btn_press, 460, Gdx.graphics.getHeight() / 2, Input.Keys.D);
        spaceButton = createCustomButton(space_idle,space_press,1360,Gdx.graphics.getHeight() / 2, Input.Keys.SPACE);
        
        // Resize the space button
        spaceButton.getButton().setSize(BUTTON_WIDTH * 2, BUTTON_HEIGHT);
    }

    private CustomButton createCustomButton(final Texture idle, final Texture press, float x, float y, final int key) {
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(idle);
        buttonStyle.imageDown = new TextureRegionDrawable(press);

        final CustomButton button = new CustomButton(new ImageButton(buttonStyle), key, idle, press);
        button.getButton().setSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        button.getButton().setPosition(x, y);

        stage.addActor(button.getButton());
        return button;
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
        if (showStart) {
            game.batch.draw(enter_key, Gdx.graphics.getWidth() / 2 - 150, 20, 300, 90);
        }
        game.batch.end();

        stage.act(delta);
        stage.draw();

        updateButtonStates();

        if (Gdx.input.isKeyPressed(Input.Keys.F12))
            Gdx.graphics.setFullscreenMode(Gdx.graphics.getDisplayMode());
        if (Gdx.input.isKeyPressed(Input.Keys.F11))
            Gdx.graphics.setWindowedMode(1280, 720);

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
        	confirm = MyGame.manager.get("audio/sfx/confirm.mp3",Sound.class);
        	confirm.play();
        	musicmenu = MyGame.manager.get("audio/music/menu-music.mp3",Music.class);
            musicmenu.stop();
            game.setScreen(new PlayScreen(game));
        }
    }

    private void updateButtonStates() {
        updateButtonState(wButton);
        updateButtonState(aButton);
        updateButtonState(sButton);
        updateButtonState(dButton);
        updateButtonState(spaceButton);
    }

    private void updateButtonState(CustomButton button) {
        if (Gdx.input.isKeyPressed(button.getKey())) {
            button.getButton().getStyle().imageUp = new TextureRegionDrawable(button.getPress());
        } else {
            button.getButton().getStyle().imageUp = new TextureRegionDrawable(button.getIdle());
        }
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}

    @Override
    public void hide() {}

    @Override
    public void dispose() {
        background.dispose();
        enter_key.dispose();
        exitTextureIdle.dispose();
        exitTextureHover.dispose();
        w_btn_idle.dispose();
        w_btn_press.dispose();
        a_btn_idle.dispose();
        a_btn_press.dispose();
        s_btn_idle.dispose();
        s_btn_press.dispose();
        d_btn_idle.dispose();
        d_btn_press.dispose();
        space_idle.dispose();
        space_press.dispose();
        stage.dispose();
    }

    private class CustomButton {
        private final ImageButton button;
        private final int key;
        private final Texture idle;
        private final Texture press;

        public CustomButton(ImageButton button, int key, Texture idle, Texture press) {
            this.button = button;
            this.key = key;
            this.idle = idle;
            this.press = press;
        }

        public ImageButton getButton() {
            return button;
        }

        public int getKey() {
            return key;
        }

        public Texture getIdle() {
            return idle;
        }

        public Texture getPress() {
            return press;
        }
    }
}
