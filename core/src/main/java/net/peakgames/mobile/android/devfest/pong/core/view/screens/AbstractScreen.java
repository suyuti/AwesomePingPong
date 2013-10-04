package net.peakgames.mobile.android.devfest.pong.core.view.screens;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;

public abstract class AbstractScreen implements Screen{

    protected Stage stage;
    protected AwesomePingPong game;
    private boolean keepAspectRatio = false;

    public AbstractScreen(AwesomePingPong game) {
        this.game = game;
        this.stage = new Stage(game.getScreenWidth(),game.getScreenHeight(),keepAspectRatio);
        Gdx.input.setInputProcessor(this.stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0f,0f,0f,1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }
    
    @Override
    public void resize(int width, int height) {
    	// TODO Auto-generated method stub
    	
    }
    

    @Override
    public void dispose() {
        this.stage.dispose();
    }
}
