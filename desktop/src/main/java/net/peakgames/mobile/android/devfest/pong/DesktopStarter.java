package net.peakgames.mobile.android.devfest.pong;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class DesktopStarter {
    private static int screenWidth = 480;
    private static int screenHeight = 800;

    public static void main(String[] args) {
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Peak Hockey";
        cfg.useGL20 = true;
        cfg.width = screenWidth;
        cfg.height = screenHeight;
        new LwjglApplication(new AwesomePingPong(screenWidth,screenHeight), cfg);
    }

}
