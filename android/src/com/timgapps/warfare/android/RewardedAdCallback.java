package com.timgapps.warfare.android;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.OnUserEarnedRewardListener;
import com.google.android.gms.ads.rewarded.RewardItem;

// аналог VideoEventListener
public interface RewardedAdCallback extends OnUserEarnedRewardListener {

    void onRewardedAdOpened();

    void onRewardedAdClosed();

    void onRewardedAdFailedToShow(AdError adError);

    @Override
    void onUserEarnedReward(@NonNull RewardItem rewardItem);

}
