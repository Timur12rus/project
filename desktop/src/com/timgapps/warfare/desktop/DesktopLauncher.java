package com.timgapps.warfare.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.timgapps.warfare.Game;
import com.timgapps.warfare.GameCallback;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.interfaces.RewardedVideoAdListener;

public class DesktopLauncher {
    public DesktopLauncher() {
        LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
        config.width = 1280;
        config.height = 720;

        new LwjglApplication(new Warfare(callback, new RewardedVideoAdListener() {
            @Override
            public boolean isErnedReward() {
                return false;
            }

            @Override
            public void resetIsErnedReward() {
            }

			@Override
			public void resetIsLoaded() {

			}

			@Override
			public boolean isLoaded() {
				return false;
			}
		}), config);
    }

    private GameCallback callback = new GameCallback() {
        @Override
        public void sendMessage(int message) {
//            System.out.println("DesktopLauncher sendMessage: " + message);

        }
    };

    public static void main(String[] arg) {
        new DesktopLauncher();
    }
}
