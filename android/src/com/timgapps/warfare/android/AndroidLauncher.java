package com.timgapps.warfare.android;

import android.annotation.TargetApi;
import android.app.Activity;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.google.android.gms.ads.rewarded.RewardItem;
import com.google.android.gms.ads.rewarded.RewardedAd;
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback;
import com.timgapps.warfare.GameCallback;
import com.timgapps.warfare.Warfare;
import com.timgapps.warfare.screens.map.interfaces.RewardedVideoAdListener;

public class AndroidLauncher extends AndroidApplication implements RewardedVideoAdListener {
    private final String ADMOB_APP_ID = "ca-app-pub-3940256099942544/5224354917";
    private RelativeLayout layout; // макет экрана с относительной разметокой
    private RewardedAd mRewardedAd;
    public RewardedAdCallback rewardedAdCallback;
    public RewardedAdLoadCallback rewardedAdLoadCallback;
    private boolean rewardedAdIsLoaded;
    private boolean isErnedReward;
    private boolean rewardedAdIsError;
    private boolean isInitializationComplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // создаем окно для андроид
        requestWindowFeature(Window.FEATURE_NO_TITLE);  // окно без заголовка
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); // получаем окно и устанавливаем флаг "окно на весь экран"
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN); // также удаляем флаг FLAG_FORCE_NOT_FULLSCREEN

        // сооздаем разметку макета окна
        layout = new RelativeLayout(this);
        View gameView = initializeForView(new Warfare(gameCallback, this));  // здесь передаем экземпляр главного класса игры
        layout.addView(gameView);    // передаем игровое окно главному окну
        setContentView(layout);

        initializeAdmob();

//        List<String> testDeviceIds = Arrays.asList("C0A9AE37B2BE90F15F47628353CE7C27");
//        List<String> testDeviceIds = Arrays.asList("33BE2250B43518CCDA7DE426D04EE231");
//        RequestConfiguration configuration =
//                new RequestConfiguration.Builder().setTestDeviceIds(testDeviceIds).build();
//        MobileAds.setRequestConfiguration(configuration);

        rewardedAdLoadCallback = new RewardedAdLoadCallback() {
            //            public void onRewardedAdLoaded() {
//            }
//
            @Override
            public void onAdLoaded(@NonNull RewardedAd rewardedAd) {
                super.onAdLoaded(rewardedAd);
                mRewardedAd = rewardedAd;
                rewardedAdIsError = false;
                mRewardedAd.setImmersiveMode(true);
                System.out.println("mRewarded = " + mRewardedAd.toString());
                System.out.println("Rewarded = " + rewardedAd.toString());
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Loaded", Toast.LENGTH_LONG).show();
                rewardedAdIsLoaded = true;
                isErnedReward = false;
            }

            @Override
            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                super.onAdFailedToLoad(loadAdError);
                Toast.makeText(AndroidLauncher.this, "Rewarded Ad is Fail Loaded!!!", Toast.LENGTH_LONG).show();
                Toast.makeText(AndroidLauncher.this, loadAdError.toString(), Toast.LENGTH_LONG).show();
                rewardedAdIsLoaded = false;
                rewardedAdIsError = true;
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
                rewardedAdIsLoaded = false;
                loadRewardedVideoAd();
            }

            @Override
            public void onUserEarnedReward(@NonNull RewardItem reward) {
                // User earned reward.
//                Toast.makeText(AndroidLauncher.this, "You won the reward :" + reward.getAmount(), Toast.LENGTH_LONG).show();
                isErnedReward = true;
                loadRewardedVideoAd();
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
//    private void loadRewardedVideoAd() {
//        rewardedAdIsError = false;
//        new RewardedVideoTask().execute();
//    }

    @Override
    public boolean isErnedReward() {
        return isErnedReward;
    }

    @Override
    public void resetIsErnedReward() {
        isErnedReward = false;
    }

    @Override
    public void resetIsLoaded() {
        rewardedAdIsLoaded = false;
        rewardedAdIsError = false;
    }

    @Override
    public boolean isLoaded() {
        rewardedAdIsError = false;
        return rewardedAdIsLoaded;
    }

    @Override
    public boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(this.CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        return ni != null && ni.isConnected();
    }

    @Override
    public boolean isError() {
        return rewardedAdIsError;
//        return isError();
    }

    @Override
    public boolean isInitializationComplete() {
        return isInitializationComplete;
    }

    @Override
    public void initializeAdmob() {
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(@NonNull InitializationStatus initializationStatus) {
                isInitializationComplete = true;
                Toast.makeText(AndroidLauncher.this, "AdMob Sdk Initialize " + initializationStatus.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    // метод для загрузки рекламного объявления
    @Override
    public void loadRewardedVideoAd() {
        rewardedAdIsError = false;
        new RewardedVideoTask().execute();
    }

    private class RewardedVideoTask extends AsyncTask<Void, Void, AdRequest> {
        @Override
        protected AdRequest doInBackground(Void... params) {
            AdRequest adRequest = new AdRequest.Builder().build();
            if (adRequest != null) {
                return adRequest;
            }
            return adRequest;
        }

        @Override
        protected void onPostExecute(AdRequest adRequest) {
            if (adRequest == null) return;
            loadReward(adRequest);

            ////////////////////
//            adRewardedVideoView = MobileAds.getRewardedVideoAdInstance(AndroidLauncher.this);
//            adRewardedVideoView.setRewardedVideoAdListener(AndroidLauncher.this);
//            adRewardedVideoView.loadAd(Setting.ADMOB_REWARDED_VIDEO, adRequest);
        }
    }

    private void loadReward(AdRequest adRequest) {
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

    @Override
    protected void onResume() {
        super.onResume();
        hideVirtualButtons();
    }
}
