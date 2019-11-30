package com.timgapps.warfare.android;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.timgapps.warfare.Game;
import com.timgapps.warfare.GameCallback;
import com.timgapps.warfare.Warfare;

public class AndroidLauncher extends AndroidApplication {
	private RelativeLayout mainView; // макет экрана с относительной разметокой

	@Override
	protected void onCreate (Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// создаем окно для андроид
		requestWindowFeature(Window.FEATURE_NO_TITLE);  // окно без заголовка
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // получаем окно и устанавливаем флаг "окно на весь экран"
		getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // также удаляем флаг FLAG_FORCE_NOT_FULLSCREEN

		// сооздаем разметку макета окна
		mainView = new RelativeLayout(this);
		setContentView(mainView);

		View gameView = initializeForView(new Warfare(callback));  // здесь передаем экземпляр главного класса игры
		mainView.addView(gameView);    // передаем игровое окно главному окну



//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//		initialize(new Game(), config);
	}

	private GameCallback callback = new GameCallback() {
		@Override
		public void sendMessage(int message) {
//            System.out.println("DesktopLauncher sendMessage: " + message);

		}
	};

}
