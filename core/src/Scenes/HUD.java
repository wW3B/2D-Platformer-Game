package Scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import th.ac.swu.game.MyGame;

public class HUD implements Disposable {

    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private static Integer score;
    private static Integer hp;

    private Texture heartTexture;
    private static Image[] hearts;

    Label countdownLabel;
    static Label scoreLabel;
    Label timeLabel;
    Label Score;

    // Define the interface
    public interface HPListener {
        void onHPZero();
    }

    private static HPListener hpListener;
    
    public interface WINListener {
        void onWIN();
    }

    private static WINListener winListener;

    public HUD(SpriteBatch sb) {
        worldTimer = 300;
        timeCount = 0;
        score = 0;
        hp = 5;

        viewport = new StretchViewport(MyGame.V_WIDTH, MyGame.V_HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        heartTexture = new Texture(Gdx.files.internal("heart.png")); // Assuming you have a heart.png file in your assets
        hearts = new Image[5];

        int heartWidth = 10; // Desired width of the heart
        int heartHeight = 10; // Desired height of the heart

        for (int i = 0; i < hearts.length; i++) {
            hearts[i] = new Image(heartTexture);
            hearts[i].setSize(heartWidth, heartHeight); // Set the size of the heart image
        }

        Table table = new Table();
        table.top();
        table.setFillParent(true);

        countdownLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel = new Label(String.format("%06d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel = new Label("TIME", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Score = new Label("SCORE", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        table.add(new Label("HP", new Label.LabelStyle(new BitmapFont(), Color.WHITE))).expandX().padTop(10);
        table.add(Score).expandX().padTop(10);
        table.add(timeLabel).expandX().padTop(10);
        table.row();

        Table hpTable = new Table();
        for (Image heart : hearts) {
            hpTable.add(heart).size(heartWidth, heartHeight).pad(5); // Explicitly set the size in the table cell
        }
        table.add(hpTable).expandX();
        table.add(scoreLabel).expandX();
        table.add(countdownLabel).expandX();

        stage.addActor(table);
    }

    public void update(float dt) {
        timeCount += dt;
        if (timeCount >= 1) {
            worldTimer--;
            countdownLabel.setText(String.format("%03d", worldTimer));
            timeCount = 0;
        }
    }
    

    public static void addScore(int value) {
        score += value;
        scoreLabel.setText(String.format("%06d", score));
        
        if (score >= 10000) {
            winListener.onWIN();
        }
    }
    
    public static void addHealth(int value) {
        hp += value;
        if (hp > 5) {
            hp = 5;
        }
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setVisible(i < hp);
        }
    }

    public static void decreaseHp(int value) {
        hp -= value;
        if (hp < 0) {
            hp = 0;
        }
        for (int i = 0; i < hearts.length; i++) {
            hearts[i].setVisible(i < hp);
        }

        // Notify the listener if HP reaches 0
        if (hp == 0 && hpListener != null) {
            hpListener.onHPZero();
        }
    }

    public static void setHPListener(HPListener listener) {
        hpListener = listener;
    }

    public static void setWINListener(WINListener listener) {
        winListener = listener;
    }

    @Override
    public void dispose() {
        stage.dispose();
        heartTexture.dispose();
    }
}
