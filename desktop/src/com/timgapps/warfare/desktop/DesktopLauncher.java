package com.timgapps.warfare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.timgapps.warfare.Game;
import com.timgapps.warfare.GameCallback;
import com.timgapps.warfare.Warfare;

public class DesktopLauncher {
	public DesktopLauncher() {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = 1280;
		config.height = 720;

		new LwjglApplication(new Warfare(callback), config);
	}

	private GameCallback callback = new GameCallback() {
		@Override
		public void sendMessage(int message) {
//            System.out.println("DesktopLauncher sendMessage: " + message);

		}
	};
	public static void main (String[] arg) {
		new DesktopLauncher();
	}
}
