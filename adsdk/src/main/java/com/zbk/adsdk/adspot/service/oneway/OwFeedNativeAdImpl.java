package com.zbk.adsdk.adspot.service.oneway;

import android.app.Activity;

import com.zbk.adsdk.adspot.service.FeedNativeAdService;
import com.zbk.adsdk.bean.FeedAdBean;
import com.zbk.adsdk.listen.FeedNativeAdListenter;

import java.util.ArrayList;
import java.util.List;

import mobi.oneway.export.Ad.OWFeedAd;
import mobi.oneway.export.AdListener.feed.OWFeedAdEventListener;
import mobi.oneway.export.AdListener.feed.OWFeedAdListener;
import mobi.oneway.export.enums.OnewaySdkError;
import mobi.oneway.export.feed.IFeedAd;

/**
 * Created by ZBK on 2021-01-26.
 * todo 渲染需要后续处理
 *
 * @function
 */
public class OwFeedNativeAdImpl implements FeedNativeAdService {

    private OWFeedAd owFeedAd;
    private FeedNativeAdListenter adListener;
    @Override
    public void initAD(Activity activity, String placementId,int count) {
        owFeedAd = new OWFeedAd(activity,placementId);
    }

    @Override
    public void load(FeedNativeAdListenter adListener) {
        this.adListener  = adListener;
        owFeedAd.load(new OWFeedAdListener() {
            @Override
            public void onError(OnewaySdkError onewaySdkError, String msg) {
                // 加载信息流广告过程中错误，可以在此保存错误日志，以便排查错误原因
                adListener.onAdFailed(msg);
            }

            @Override
            public void onAdLoad(List<IFeedAd> feedAds) {
                List<FeedAdBean> feedAdBeanList  = new ArrayList<>();
                //加载信息流广告成功
                for (int i = 0; i < feedAds.size(); i++) {
                    FeedAdBean feedAdBean = new FeedAdBean();
                    feedAdBean.setTitle(feedAds.get(i).getTitle());
                    feedAdBean.setIconImage(feedAds.get(i).getIconImage());
                    feedAdBean.setImages(feedAds.get(i).getImages());
                    feedAds.get(i).handleAdEvent(feedAdBean.getConvertView(), new OWFeedAdEventListener() {
                        @Override
                        public void onExposured(IFeedAd iFeedAd) {
                            if( feedAdBean.getOwFeedAdEventListener()!=null){
                                feedAdBean.getOwFeedAdEventListener().onExposured();
                            }
                        }

                        @Override
                        public void onClicked(IFeedAd iFeedAd) {
                            if( feedAdBean.getOwFeedAdEventListener()!=null){
                                feedAdBean.getOwFeedAdEventListener().onClicked();
                            }
                        }
                    });
                      feedAdBeanList.add(feedAdBean);
                }
                adListener.onAdLoad(feedAdBeanList);
            }
        });
    }
}
