package net.peakgames.mobile.android.devfest.pong.core.view.screens;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameScreen extends AbstractScreen{

	private Image ball;
	private Image topBar;
	private Image bottomBar;
		
	public GameScreen(AwesomePingPong game) {
		super(game);
	}

	@Override
	public void show() {
		
		Image bgImage = new Image(new Texture(Gdx.files.internal("gameBg.png")));
		this.stage.addActor(bgImage);
		
		ball = new Image(new Texture(Gdx.files.internal("pongball.png")));
		topBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		bottomBar = new Image(new Texture(Gdx.files.internal("bar.png")));
		
		topBar.setPosition(177, 780);
		bottomBar.setPosition(177, 10);
		ball.setPosition(220, 380);
		
		this.stage.addActor(topBar);
		this.stage.addActor(bottomBar);
		this.stage.addActor(ball);
		
		this.stage.addListener(new ClickListener(){
			
			@Override
			public boolean touchDown(InputEvent event, float x, float y,
					int pointer, int button) {
				//Prevent bar from going off screen
				float newX = x - bottomBar.getWidth() / 2;
				if (x >= game.getScreenWidth() - bottomBar.getWidth() / 2) {
					bottomBar.setX(game.getScreenWidth() - bottomBar.getWidth());
				} else if (x <= 0 + bottomBar.getWidth() / 2) {
					bottomBar.setX(0);
				} else {
					bottomBar.setX(x - bottomBar.getWidth() / 2);
				}
				return true;
			}
			
			@Override
			public void touchDragged(InputEvent event, float x, float y,
					int pointer) {
				//Prevent bar from going off screen
				float newX = x - bottomBar.getWidth() / 2;
				if (x >= game.getScreenWidth() - bottomBar.getWidth() / 2) {
					bottomBar.setX(game.getScreenWidth() - bottomBar.getWidth());
				} else if (x <= 0 + bottomBar.getWidth() / 2) {
					bottomBar.setX(0);
				} else {
					bottomBar.setX(x - bottomBar.getWidth() / 2);
				}
			}
		});
		
	}
	
	

	@Override
	public void hide() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

}
