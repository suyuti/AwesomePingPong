package net.peakgames.mobile.android.devfest.pong.java;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import net.peakgames.mobile.android.devfest.pong.core.GameStarter;

public class GameStarterDesktop {
	public static void main (String[] args) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.useGL20 = true;
		new LwjglApplication(new GameStarter(), config);
	}
}
