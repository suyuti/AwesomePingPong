package net.peakgames.mobile.android.devfest.pong.core;

import net.peakgames.mobile.android.devfest.pong.core.view.screens.GameScreen;
import net.peakgames.mobile.android.devfest.pong.core.view.screens.MenuScreen;
import net.peakgames.mobile.android.devfest.pong.core.view.screens.SplashScreen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

public class AwesomePingPong extends Game {

    private static String TAG = "AwesomePingPong";
    private float screenWidth;
    private float screenHeight;

    public AwesomePingPong(float screenWidth, float screenHeight) {
        this.screenWidth = screenWidth;
        this.screenHeight = screenHeight;
    }

    @Override
    public void create() {
        Gdx.app.log(TAG, "Creating game..");
        setScreen(getSplashScreen());
    }

    public Screen getSplashScreen() {
        return new SplashScreen(this);
    }

    public Screen getMenuScreen() {
        return new MenuScreen(this);
    }
    
    public Screen getGameScreen() {
        return new GameScreen(this);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width,height);
    }

    public void render () {
        super.render();
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    public float getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(float screenHeight) {
        this.screenHeight = screenHeight;
    }

    public float getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(float screenWidth) {
        this.screenWidth = screenWidth;
    }
}
