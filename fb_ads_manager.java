package com.example.guidd;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.facebook.ads.Ad;
import com.facebook.ads.AdError;
import com.facebook.ads.AdOptionsView;
import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.InterstitialAd;
import com.facebook.ads.InterstitialAdListener;
import com.facebook.ads.MediaView;
import com.facebook.ads.NativeAd;
import com.facebook.ads.NativeAdLayout;
import com.facebook.ads.NativeAdListener;
import com.facebook.ads.NativeBannerAd;
import com.facebook.ads.NativeBannerAdView;
import com.facebook.ads.RewardedVideoAd;
import com.facebook.ads.RewardedVideoAdListener;

import java.util.ArrayList;
import java.util.List;

/**
 *  The FB_ADS_MANAGER or FACEBOOK ADS MANAGER is
 *  A class that was built to help managing all application's ADS
 *  From one place, in order to help improving the debuging and production
 *
 * @author      MEHDI REYSEY
 * @since       2020
 * @G-Mail      MehdiReysey@gmail.com
 * @Facebook    www.facebook.com/The.Mehdi.Reysey/
 * @LinkedIn    www.linkedin.com/in/mehdireysey/
 * @CopyRight   By using this class you have the right full control of it.
 */
public class fb_ads_manager {

    // DEBUGING VARIABLES
    private final   String          TAG;

    // CLASS VARIABLES
    private         Context         context;

    // ADS VARIABLE DECLARATION
    private         InterstitialAd  interstitialAd;
    private         NativeAd        nativeAd;
    private         NativeAdLayout  nativeAdLayout;
    private         LinearLayout    linearLayoutAdView;
    private         LinearLayout    adChoicesContainer;
    private         NativeBannerAd  BannerNativeAd;
    private         LinearLayout    nativeBannerAdContainer;
    private         View            _NativeBannerAdView;
    private         AdView            _BannerAdView;
    private         LinearLayout    BannerAdContainer;
    private         RewardedVideoAd rewardedVideoAd;


    /**
     * This is the class constructor.
     * @param context context of the class/activity.
     */
    public fb_ads_manager(Context context) {
        this.context                    = context;
        this.interstitialAd             = null;
        this.nativeAd                   = null;
        this.nativeAdLayout             = null;
        this.linearLayoutAdView         = null;
        this.adChoicesContainer         = null;
        this.BannerNativeAd             = null;
        this.nativeBannerAdContainer    = null;
        this._NativeBannerAdView        = null;
        this._BannerAdView              = null;
        this.BannerAdContainer          = null;
        this.rewardedVideoAd            = null;
        this.TAG                        = "TAG: [ "+context.getClass().getSimpleName()+" ] REY-DEBUG";
    }

    /**
     * This method will destroy all view resources...
     */
    public void destroy() {

        // NATIVE BANNER AD
        if(this.BannerNativeAd != null){
            this.BannerNativeAd.destroy();
        }

        // INTERSTITIAL AD
        if(this.interstitialAd != null){
            this.interstitialAd.destroy();
        }

        // NATIVE AD
        if(this.nativeAd != null){
            this.nativeAd.destroy();
        }

        // BANNER AD
        if(this._BannerAdView != null){
            this._BannerAdView.destroy();
        }

        // REWARDED AD
        if(this.rewardedVideoAd != null){
            this.rewardedVideoAd.destroy();
        }
    }

    // FB INTERSTITIAL AD

    /**
     * This method will display an interstitial ad in the activity where it is called.
     * @param placementId FACEBOOK PLACEMENT ID FOR THE INTERSTITIAL AD
     */
    public void interstitialAd(String placementId){
        interstitialAd = new InterstitialAd(context, placementId);

        // Create listeners for the Interstitial Ad
        InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
            @Override
            public void onInterstitialDisplayed(Ad ad) {
                // Interstitial ad displayed callback
                Log.e(TAG, "Interstitial ad displayed.");
            }

            @Override
            public void onInterstitialDismissed(Ad ad) {
                // Interstitial dismissed callback
                Log.e(TAG, "Interstitial ad dismissed.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Ad error callback
                Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Interstitial ad is loaded and ready to be displayed
                Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");

                // onAdLoadedSuccess
                //onAdLoadedSuccess();

                // ***********
                // Show the ad
                interstitialAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Ad clicked callback
                Log.d(TAG, "Interstitial ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Ad impression logged callback
                Log.d(TAG, "Interstitial ad impression logged!");
            }
        };

        // For auto play video ads, it's recommended to load the ad
        // at least 30 seconds before it is shown
        interstitialAd.loadAd(
                interstitialAd.buildLoadAdConfig()
                        .withAdListener(interstitialAdListener)
                        .build());
    }

    // FB NATIVE AD

    /**
     * This method will display the native ad when called.
     * @param placementId FACEBOOK PLACEMENT ID FOR THE NATIVE AD
     * @param view THE NATIVE AD VIEW CONTAINER USUALLY BE LIKE 'findViewById(R.id.native_ad_container)
     */
    public void nativeAd(String placementId,final View view){

        nativeAd = new NativeAd(context, placementId);

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");

                // EVENT MANAGER
                // nativeAdEventManager();
                nativeAdEvents(context.getClass().getSimpleName(),"onAdLoaded");

                // Race condition, load() called again before last ad was displayed
                if (nativeAd == null || nativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                inflateAd(nativeAd, view);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };

        // Request an ad
        nativeAd.loadAd(
                nativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    private void inflateAd(NativeAd nativeAd, View view) {

        nativeAd.unregisterView();

        // Add the Ad view into the ad container.
        nativeAdLayout = view.findViewById(R.id.native_ad_container);
        LayoutInflater inflater = LayoutInflater.from(context);
        // Inflate the Ad view.  The layout referenced should be the one you created in the last step.
        linearLayoutAdView = (LinearLayout) inflater.inflate(R.layout.native_ad_layout, nativeAdLayout, false);
        nativeAdLayout.addView(linearLayoutAdView);

        // Add the AdOptionsView
        adChoicesContainer = view.findViewById(R.id.ad_choices_container);
        AdOptionsView adOptionsView = new AdOptionsView(context, nativeAd, nativeAdLayout);
        adChoicesContainer.removeAllViews();
        adChoicesContainer.addView(adOptionsView, 0);

        // Create native UI using the ad metadata.
        MediaView nativeAdIcon = linearLayoutAdView.findViewById(R.id.native_ad_icon);
        TextView nativeAdTitle = linearLayoutAdView.findViewById(R.id.native_ad_title);
        MediaView nativeAdMedia = linearLayoutAdView.findViewById(R.id.native_ad_media);
        TextView nativeAdSocialContext = linearLayoutAdView.findViewById(R.id.native_ad_social_context);
        TextView nativeAdBody = linearLayoutAdView.findViewById(R.id.native_ad_body);
        TextView sponsoredLabel = linearLayoutAdView.findViewById(R.id.native_ad_sponsored_label);
        Button nativeAdCallToAction = linearLayoutAdView.findViewById(R.id.native_ad_call_to_action);

        // Set the Text.
        nativeAdTitle.setText(nativeAd.getAdvertiserName());
        nativeAdBody.setText(nativeAd.getAdBodyText());
        nativeAdSocialContext.setText(nativeAd.getAdSocialContext());
        nativeAdCallToAction.setVisibility(nativeAd.hasCallToAction() ? View.VISIBLE : View.INVISIBLE);
        nativeAdCallToAction.setText(nativeAd.getAdCallToAction());
        sponsoredLabel.setText(nativeAd.getSponsoredTranslation());

        // Create a list of clickable views
        List<View> clickableViews = new ArrayList<>();
        clickableViews.add(nativeAdTitle);
        clickableViews.add(nativeAdCallToAction);

        // Register the Title and CTA button to listen for clicks.
        nativeAd.registerViewForInteraction(
                linearLayoutAdView, nativeAdMedia, nativeAdIcon, clickableViews);
    }

    // FB NATIVE BANNER AD

    /**
     * This method will display native banner ad when called.
     * @param placementId FACEBOOK PLACEMENT ID FOR THE NATIVE BANNER AD
     * @param view THE NATIVE BANNER AD VIEW CONTAINER USUALLY BE LIKE 'findViewById(R.id.native_banner_ad_container)
     */
    public void nativeBannerAd(String placementId, final View view){
        BannerNativeAd = new NativeBannerAd(context, placementId);

        NativeAdListener nativeAdListener = new NativeAdListener() {
            @Override
            public void onMediaDownloaded(Ad ad) {
                // Native ad finished downloading all assets
                Log.e(TAG, "Native ad finished downloading all assets.");
            }

            @Override
            public void onError(Ad ad, AdError adError) {
                // Native ad failed to load
                Log.e(TAG, "Native ad failed to load: " + adError.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Native ad is loaded and ready to be displayed
                Log.d(TAG, "Native ad is loaded and ready to be displayed!");

                // onAdLoadedSuccess
                //onAdLoadedSuccess();

                // Race condition, load() called again before last ad was displayed
                if (BannerNativeAd == null || BannerNativeAd != ad) {
                    return;
                }
                // Inflate Native Ad into Container
                //inflateBannerAd(BannerNativeAd);
                // Render the Native Banner Ad Template
                _NativeBannerAdView = NativeBannerAdView.render(context, BannerNativeAd, NativeBannerAdView.Type.HEIGHT_100);
                nativeBannerAdContainer = (LinearLayout) view.findViewById(R.id.native_banner_ad_container);
                // Add the Native Banner Ad View to your ad container
                nativeBannerAdContainer.addView(_NativeBannerAdView);
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Native ad clicked
                Log.d(TAG, "Native ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Native ad impression
                Log.d(TAG, "Native ad impression logged!");
            }
        };

        // Request an ad
        BannerNativeAd.loadAd(
                BannerNativeAd.buildLoadAdConfig()
                        .withAdListener(nativeAdListener)
                        .build());
    }

    // FB BANNER AD

    /**
     * This method will display banner ad when called.
     * @param placementId FACEBOOK PLACEMENT ID FOR THE BANNER AD
     * @param view THE BANNER AD VIEW CONTAINER USUALLY BE LIKE 'findViewById(R.id.banner_ad_container)
     */
    public void bannerAd(String placementId, View view){
        // ***

        _BannerAdView = new AdView(context, placementId, AdSize.BANNER_HEIGHT_50);

        // Find the Ad Container
        BannerAdContainer = (LinearLayout) view.findViewById(R.id.banner_container);

        // Add the ad view to your activity layout
        BannerAdContainer.addView(_BannerAdView);

        // Request an ad
        _BannerAdView.loadAd();

        // ***
    }

    // FB REWARDED AD
    /**
     * This method will display a rewarded ad when called.
     * @param placementId FACEBOOK PLACEMENT ID FOR THE REWARDED AD
     */
    public void rewardedAd(String placementId){
        rewardedVideoAd = new RewardedVideoAd(context, placementId);

        // ***
        RewardedVideoAdListener rewardedVideoAdListener = new RewardedVideoAdListener() {
            @Override
            public void onError(Ad ad, AdError error) {
                // Rewarded video ad failed to load
                Log.e(TAG, "Rewarded video ad failed to load: " + error.getErrorMessage());
            }

            @Override
            public void onAdLoaded(Ad ad) {
                // Rewarded video ad is loaded and ready to be displayed
                Log.d(TAG, "Rewarded video ad is loaded and ready to be displayed!");

                // Rewarded video ad is loaded and ready to be displayed
                rewardedVideoAd.show();
            }

            @Override
            public void onAdClicked(Ad ad) {
                // Rewarded video ad clicked
                Log.d(TAG, "Rewarded video ad clicked!");
            }

            @Override
            public void onLoggingImpression(Ad ad) {
                // Rewarded Video ad impression - the event will fire when the
                // video starts playing
                Log.d(TAG, "Rewarded video ad impression logged!");
            }

            @Override
            public void onRewardedVideoCompleted() {
                // Rewarded Video View Complete - the video has been played to the end.
                // You can use this event to initialize your reward
                Log.e(TAG, "Rewarded video completed!");

                // Call method to give reward
                // giveReward();
            }

            @Override
            public void onRewardedVideoClosed() {
                // The Rewarded Video ad was closed - this can occur during the video
                // by closing the app, or closing the end card.
                Log.d(TAG, "Rewarded video ad closed!");
            }
        };
        rewardedVideoAd.loadAd(
                rewardedVideoAd.buildLoadAdConfig()
                        .withAdListener(rewardedVideoAdListener)
                        .build());
    }


    // EVENTS MANAGEMENT

    // NATIVE AD EVENTS MANAGEMENT

    private View nativeAdEventManager_view = null;
    public void set_nativeBannerAdView(View view){
        nativeAdEventManager_view = view;
    }

    public void nativeAdEvents(String activityName ,String eventName){

        switch (eventName){
            case "onMediaDownloaded"    : /* onMediaDownloaded      EVENT TRIGGERED THIS CASE WILL BE USED */
                ;break;

            case "onError"              : /* onError                EVENT TRIGGERED THIS CASE WILL BE USED */
                ;break;

            case "onAdLoaded"           : /* onAdLoaded             EVENT TRIGGERED THIS CASE WILL BE USED */

                switch (activityName){
                    case "MainActivity": /* WHEN EVENT TRIGGERED INSIDE THIS ACTIVITY THIS CASE WILL BE APPLIED */;
                        if(nativeAdEventManager_view != null){
                            RelativeLayout loading = (RelativeLayout) nativeAdEventManager_view.findViewById(R.id.loading_layout);
                            loading.setVisibility(View.GONE);
                        }else{
                            Log.e(TAG, "You should call set_nativeBannerAdView and it the needed view First Then Call nativeAdEventManager");
                        }
                    break;
                }
                ;break;

            case "onAdClicked"          : /* onAdClicked            EVENT TRIGGERED THIS CASE WILL BE USED */
                ;break;

            case "onLoggingImpression"  : /* onLoggingImpression    EVENT TRIGGERED THIS CASE WILL BE USED */
                ;break;

            default: Log.e(TAG, "eventName unRecognizable, Make sure the called event is defined in the nativeAdEvents method.");
        }
    }


}

