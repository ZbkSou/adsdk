package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;

import com.zbk.adsdk.AdListenter;
import com.zbk.adsdk.adspot.service.FeedAdService;

import java.util.List;

import mobi.oneway.export.Ad.OWFeedAd;
import mobi.oneway.export.Ad.OWInteractiveAd;
import mobi.oneway.export.AdListener.feed.OWFeedAdListener;
import mobi.oneway.export.enums.OnewaySdkError;
import mobi.oneway.export.feed.IFeedAd;

/**
 * Created by ZBK on 2021-01-26.
 * todo 渲染需要后续处理
 *
 * @function
 */
public class OwFeedAdImpl implements FeedAdService {

    private OWFeedAd owFeedAd;
    @Override
    public void initAD(Activity activity, String placementId) {
        owFeedAd = new OWFeedAd(activity,placementId);
    }

    @Override
    public void load(AdListenter adListener) {
        owFeedAd.load(new OWFeedAdListener() {
            @Override
            public void onError(OnewaySdkError onewaySdkError, String msg) {
                // 加载信息流广告过程中错误，可以在此保存错误日志，以便排查错误原因
            }

            @Override
            public void onAdLoad(List<IFeedAd> feedAds) {
                //加载信息流广告成功
            }
        });
    }
}
