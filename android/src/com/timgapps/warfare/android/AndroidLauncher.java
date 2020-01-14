package com.timgapps.warfare.android;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.os.Build;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создаем окно для андроид
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // окно без заголовка

//        if (Build.VERSION.SDK_INT < 16) {
//            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
//        } else {
//            View decorView = getWindow().getDecorView();
////            int uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN;
//            decorView.setSystemUiVisibility(uiOptions);
////            ActionBar actionBar = getActionBar();
////            actionBar.hide();
//        }



        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // получаем окно и устанавливаем флаг "окно на весь экран"
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // также удаляем флаг FLAG_FORCE_NOT_FULLSCREEN


        // In KITKAT (4.4) and next releases, hide the virtual buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideVirtualButtons();
        }

//        hideStatusBar = true;

//        hideStatusBar
                // сооздаем разметку макета окна
                mainView = new RelativeLayout(this);
        setContentView(mainView);

//        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        config.useImmersiveMode = true;
//        config.hideStatusBar = false;
        View gameView = initializeForView(new Warfare(callback));  // здесь передаем экземпляр главного класса игры
//        View gameView = initializeForView(new Warfare(callback), config);  // здесь передаем экземпляр главного класса игры
        mainView.addView(gameView);    // передаем игровое окно главному окну


//		AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
//        config.hideStatusBar = true;
//		initialize(new Game(), config);

    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // In KITKAT (4.4) and next releases, hide the virtual buttons
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                hideVirtualButtons();
            }
        }
    }

    @TargetApi(19)
    private void hideVirtualButtons() {
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    private GameCallback callback = new GameCallback() {
        @Override
        public void sendMessage(int message) {
//            System.out.println("DesktopLauncher sendMessage: " + message);
        }
    };

}
