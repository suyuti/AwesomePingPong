package net.peakgames.mobile.android.devfest.pong.core.view.screens;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.scenes.scene2d.ui.Image;

public class SplashScreen extends AbstractScreen {

    private Texture splashTexture;
	private Image splashImage;

    public SplashScreen(AwesomePingPong game) {
       super(game);
    }

    @Override
    public void show() {
    	
        initializeSplashImage();
        addSplashAnimation();
        this.stage.addActor(splashImage);

    }

	private void addSplashAnimation() {
			
        SequenceAction splashAction = new SequenceAction();
        splashAction.addAction(Actions.fadeOut(0f));
        splashAction.addAction(Actions.fadeIn(1f));
        splashAction.addAction(Actions.delay(0.5f));
        splashAction.addAction(Actions.fadeOut(1f));
        splashAction.addAction(new Action() {
            @Override
            public boolean act(float delta) {
                game.setScreen(game.getMenuScreen());
                return false;
            }
        });
        splashImage.addAction(splashAction);
		
	}

	private void initializeSplashImage() {
		Texture splashTexture = new Texture(Gdx.files.internal("splash_peak_logo.png"));
        splashImage = new Image(new TextureRegion(splashTexture));
        float splashImagePositionX = game.getScreenWidth()/2 - (splashImage.getWidth()/2);
        float splashImagePositionY = game.getScreenHeight()/2 - (splashImage.getHeight()/2);
    	splashImage.setPosition(splashImagePositionX, splashImagePositionY);
	}

    @Override
    public void render(float delta )
    {
        super.render(delta);
    }

    @Override
    public void dispose()
    {
    }


    @Override
    public void resize(int width, int height) {
        //To change body of implemented methods use File | Settings | File Templates.
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

}
