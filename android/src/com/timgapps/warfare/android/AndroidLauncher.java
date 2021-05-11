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
    RewardedAdCallback rewardedAdCallback;
    RewardedAdLoadCallback rewardedAdLoadCallback;
    private boolean rewardedAdIsLoaded;

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

        rewardedAdLoadCallback = new RewardedAdLoadCallback() {
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mRewardedAd = rewardedAd;
//                Log.d("TAG", mRewardedAd.toString());
//                Log.d("TAG", rewardedAd.toString());
                System.out.println("mRewarded = " + mRewardedAd.toString());
                System.out.println("Rewarded = " + rewardedAd.toString());
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
                rewardedAdIsLoaded = true;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Fail Loaded!!!", Toast.LENGTH_LONG).show();
                rewardedAdIsLoaded = false;
            }
        };

        rewardedAdCallback = new RewardedAdCallback() {
            @Override
            public void onRewardedAdOpened() {
                // Ad opened.
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Opened", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedAdClosed() {
                // Ad closed.
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad Closed", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
                Toast.makeText(AndroidLauncher.this, "You won the reward :" + reward.getAmount(), Toast.LENGTH_LONG).show();
            }

            @Override
            public void onRewardedAdFailedToShow(AdError adError) {
                // Ad failed to display.
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad failed to show due to error:" + adError.toString(), Toast.LENGTH_LONG).show();
            }
        };

        // In KITKAT (4.4) and next releases, hide the virtual buttons
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            hideVirtualButtons();
        }
        loadRewardedVideoAd();
    }

    // инициализируем интерфейс GameCallBack() для организации взаимодействия
    private GameCallback gameCallback = new GameCallback() {
        @Override
        public void sendMessage(int message) { // переопределяем метод sendMessage, в котором будем проверять какая константа
            // приходит из главного класса игры
            if (message == Warfare.LOAD_REWARDED_VIDEO) {
                loadRewardedVideoAd();
            } else if (message == Warfare.SHOW_REWARDED_VIDEO) {
//                AndroidLauncher.this.runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
                showRewardedVideoAd();
//                    }
//                });
            }
        }
    };

    // метод для загрузки рекламного объявления
    private void loadRewardedVideoAd() {
        if (!rewardedAdIsLoaded) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    loadReward();
                }
            });


//        mRewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
//            @Override
//            public void onAdShowedFullScreenContent() {
//                // Called when ad is shown.
//                Log.d(TAG, "Ad was shown.");
//                mRewardedAd = null;
//            }
//
//            @Override
//            public void onAdFailedToShowFullScreenContent(AdError adError) {
//                // Called when ad fails to show.
//                Log.d(TAG, "Ad failed to show.");
//            }
//
//            @Override
//            public void onAdDismissedFullScreenContent() {
//                // Called when ad is dismissed.
//                // Don't forget to set the ad reference to null so you
//                // don't show the ad a second time.
//                Log.d(TAG, "Ad was dismissed.");
//            }
//
////            @Override
////            public void onWindowFocusChanged(boolean hasFocus) {
////                super.onWindowFocusChanged(hasFocus);
////                if (hasFocus) {
////                    // In KITKAT (4.4) and next releases, hide the virtual buttons
////                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
////                        hideVirtualButtons();
////                    }
////                }
////            }
//        });
        }
    }

    private void loadReward() {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(this, ADMOB_APP_ID,
                adRequest, rewardedAdLoadCallback);
    }

    // метод для показа объявления с вознаграждением
    public void showRewardedVideoAd() {
        if (rewardedAdIsLoaded) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Activity activityContext = getParent();
                    mRewardedAd.show(activityContext, rewardedAdCallback);
//                    if (mRewardedAd != null) {
////                    if (mRewardedAd != null) {
//                        Activity activityContext = getParent();
//                        mRewardedAd.show(activityContext, rewardedAdCallback);
//                    } else {
//                        loadRewardedVideoAd();
//                    }
                }
            });
        } else {
            // загружаем видео, если оно не загружено
//            Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
//            Toast.makeText(AndroidLauncher.this, "Rewarded Ad is not Loaded ", Toast.LENGTH_LONG).show();
            loadRewardedVideoAd();
        }
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
