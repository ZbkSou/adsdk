package com.zbk.adsdk.bean;

import android.view.ViewGroup;

import com.zbk.adsdk.listen.FeedAdEventListener;

import java.util.List;

/**
 * Created by ZBK on 2021-01-28.
 *
 * @function
 */
public class FeedAdBean {


    String title;

    List<String> images;
    String  iconImage;
    ViewGroup convertView;
    FeedAdEventListener owFeedAdEventListener;

    public ViewGroup getConvertView() {
        return convertView;
    }

    public void setConvertView(ViewGroup convertView) {
        this.convertView = convertView;
    }

    public FeedAdEventListener getOwFeedAdEventListener() {
        return owFeedAdEventListener;
    }

    public void setOwFeedAdEventListener(FeedAdEventListener owFeedAdEventListener) {
        this.owFeedAdEventListener = owFeedAdEventListener;
    }

    public FeedAdBean() {
        this.convertView = null;
        this.owFeedAdEventListener = null ;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public String getIconImage() {
        return iconImage;
    }

    public void setIconImage(String iconImage) {
        this.iconImage = iconImage;
    }

    //处理广告曝光和点击 SDK接管convertView的点击和曝光处理 必须要调用
    void handleAdEvent(ViewGroup convertView, FeedAdEventListener owFeedAdEventListener){
         this.convertView = convertView;
        this.owFeedAdEventListener=owFeedAdEventListener ;
    };
}
