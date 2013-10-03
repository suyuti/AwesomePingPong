package net.peakgames.mobile.android.devfest.pong.html;

import net.peakgames.mobile.android.devfest.pong.core.GameStarter;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

public class GameStarterHtml extends GwtApplication {
	@Override
	public ApplicationListener getApplicationListener () {
		return new GameStarter();
	}
	
	@Override
	public GwtApplicationConfiguration getConfig () {
		return new GwtApplicationConfiguration(480, 320);
	}
}
