package com.timgapps.warfare.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.OnPaidEventListener;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.ResponseInfo;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.OnAdMetadataChangedListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.google.android.gms.ads.rewarded.ServerSideVerificationOptions;
import com.timgapps.warfare.GameCallback;
import com.timgapps.warfare.Warfare;

import static android.content.ContentValues.TAG;

public class AndroidLauncher extends AndroidApplication {
    private final String ADMOB_APP_ID = "ca-app-pub-3940256099942544/5224354917";
    private RelativeLayout layout; // макет экрана с относительной разметокой
    private RewardedAd mRewardedAd;
    private RewardedAd rewardedAd;
    RewardedAdCallback rewardedAdCallback;
    RewardedAdLoadCallback rewardedAdLoadCallback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создаем окно для андроид
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // окно без заголовка
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // получаем окно и устанавливаем флаг "окно на весь экран"
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // также удаляем флаг FLAG_FORCE_NOT_FULLSCREEN

        // сооздаем разметку макета окна
        layout = new RelativeLayout(this);
        View gameView = initializeForView(new Warfare(gameCallback));  // здесь передаем экземпляр главного класса игры
        layout.addView(gameView);    // передаем игровое окно главному окну
        setContentView(layout);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                Toast.makeText(AndroidLauncher.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
            }
        });
        rewardedAd = new RewardedAd(this, ADMOB_APP_ID);

        setupAds();

        // In KITKAT (4.4) and next releases, hide the virtual buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideVirtualButtons();
        }
    }

    // инициализируем интерфейс GameCallBack() для организации взаимодействия
    private GameCallback gameCallback = new GameCallback() {
        @Override
        public void sendMessage(int message) { // переопределяем метод sendMessage, в котором будем проверять какая константа
            // приходит из главного класса игры
            if (message == Warfare.LOAD_REWARDED_VIDEO) {
                loadRewardedVideoAd();
            } else if (message == Warfare.SHOW_REWARDED_VIDEO) {
                AndroidLauncher.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        showRewardedVideoAd();
                    }
                });
            }
        }
    };

    // метод для показа объявления с вознаграждением
    public void showRewardedVideoAd() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (mRewardedAd != null) {
                    Activity activityContext = getParent();
                    RewardedAdCallback adCallback = new RewardedAdCallback() {
                        @Override
                        public void onRewardedAdOpened() {
                            // Ad opened.
                        }

                        @Override
                        public void onRewardedAdClosed() {
                            // Ad closed.
                        }

                        @Override
                        public void onUserEarnedReward(@NonNull RewardItem reward) {
                            // User earned reward.
                        }

                        @Override
                        public void onRewardedAdFailedToShow(AdError adError) {
                            // Ad failed to display.
                        }
                    };
                    mRewardedAd.show(activityContext, adCallback);
                } else {
                    loadRewardedVideoAd();
                }
            }
        });
    }

    // метод для установки настроек рекламы
    public void setupAds() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.");
                mRewardedAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Don't forget to set the ad reference to null so you
                // don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.");
            }
        });
    }

    // метод для загрузки рекламного объявления
    private void loadRewardedVideoAd() {
        RewardedAd.load(this, "ca-app-pub-3940256099942544/5224354917",
                adRequest, new RewardedAdLoadCallback() {
                    @Override
                    public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                        // Handle the error.
                        Log.d(TAG, loadAdError.getMessage());
                        mRewardedAd = null;
                    }

                    @Override
                    public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                        mRewardedAd = rewardedAd;
                        Log.d(TAG, "Ad was loaded.");
                    }
                });

        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
            @Override
            public void onAdShowedFullScreenContent() {
                // Called when ad is shown.
                Log.d(TAG, "Ad was shown.");
                mRewardedAd = null;
            }

            @Override
            public void onAdFailedToShowFullScreenContent(AdError adError) {
                // Called when ad fails to show.
                Log.d(TAG, "Ad failed to show.");
            }

            @Override
            public void onAdDismissedFullScreenContent() {
                // Called when ad is dismissed.
                // Don't forget to set the ad reference to null so you
                // don't show the ad a second time.
                Log.d(TAG, "Ad was dismissed.");
            }

//            @Override
//            public void onWindowFocusChanged(boolean hasFocus) {
//                super.onWindowFocusChanged(hasFocus);
//                if (hasFocus) {
//                    // In KITKAT (4.4) and next releases, hide the virtual buttons
//                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                        hideVirtualButtons();
//                    }
//                }
//            }
        });
    }

    // метод скрывает кнопки виртуальные с экрана
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
}
