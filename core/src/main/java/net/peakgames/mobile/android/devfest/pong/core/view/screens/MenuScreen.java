package net.peakgames.mobile.android.devfest.pong.core.view.screens;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.MoveToAction;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MenuScreen extends AbstractScreen {

    private static final float TITLE_SCREEN_HEIGHT_RATIO = 0.66f;

	public MenuScreen(AwesomePingPong game) {
       super(game);
    }

    @Override
    public void show() {

    	Image backGround = new Image(new TextureRegion(new Texture(Gdx.files.internal("BG.png"))));
    	this.stage.addActor(backGround);
    	
    	
        Button playButton = getButton("Play", "greenButton.png", "greenButtonH.png");
        Button introButton = getButton("Intro", "blackButton.png", "blackButtonH.png");
        Button exitButton = getButton("Exit", "redButton.png", "redButtonH.png");
        
        setButtonListeners(playButton, introButton, exitButton);

        Table menuTable = new Table();
        menuTable.add(playButton).padBottom(5);
        menuTable.row();
        menuTable.add(introButton).padBottom(5);
        menuTable.row();
        menuTable.add(exitButton);
        menuTable.row();

        menuTable.setPosition(game.getScreenWidth()*1.5f, 300);
        addTableAction(menuTable);
        this.stage.addActor(menuTable);

        addGameTitle();


    }

	private void addGameTitle() {
		Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(Gdx.files.internal("steelfish.fnt"), Gdx.files.internal("steelfish.png"),false);
        Label title = new Label("Awesome Ping Pong!", labelStyle);
        title.setWidth(game.getScreenWidth());
        title.setAlignment(Align.center);
        title.setPosition(0, game.getScreenHeight()*1.5f);
        title.addAction(Actions.moveTo(0,game.getScreenHeight()*TITLE_SCREEN_HEIGHT_RATIO,2f,Interpolation.elastic));
        this.stage.addActor(title);
	}

	private void setButtonListeners(Button playButton, Button introButton,
			Button exitButton) {
		playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
            	game.setScreen(game.getGameScreen());
            }
        });

        introButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(game.getSplashScreen());
            }
        });

        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
	}

    private Button getButton(String text, String upName, String downName) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();
        buttonStyle.up = new TextureRegionDrawable(new TextureRegion(new Texture(upName)));
        buttonStyle.down = new TextureRegionDrawable(new TextureRegion(new Texture(downName)));
        buttonStyle.font = new BitmapFont();
        TextButton button = new TextButton(text,buttonStyle);
        return button;
    }

    private void addTableAction(Table table) {
        MoveToAction moveToAction = new MoveToAction();
        moveToAction.setX(game.getScreenWidth() / 2);
        moveToAction.setY(table.getY());
        moveToAction.setDuration(0.5f);
        moveToAction.setInterpolation(Interpolation.elastic);
        table.addAction(moveToAction);
    }

    @Override
    public void hide() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void pause() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void resume() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void render(float delta )
    {
        super.render(delta);
    }



}
