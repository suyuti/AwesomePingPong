package net.peakgames.mobile.android.devfest.pong;

import net.peakgames.mobile.android.devfest.pong.core.AwesomePingPong;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class AwesomePingPongActivity extends AndroidApplication {
    /**
     * Called when the activity is first created.
     */
    
    private static int screenWidth = 480;
    private static int screenHeight = 800;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AndroidApplicationConfiguration cfg = new AndroidApplicationConfiguration();
        cfg.useAccelerometer = false;
        cfg.useCompass = false;
        cfg.useWakelock = true;
        cfg.useGL20 = true;
        initialize(new AwesomePingPong(screenWidth,screenHeight), cfg);

    }
}
