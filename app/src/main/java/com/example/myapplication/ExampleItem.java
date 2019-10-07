package com.example.myapplication;

import java.io.Serializable;

public class ExampleItem implements Serializable {
    private String mImageUrl;
    private String mCreator;


    private String mDescription;

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    private String mTime;

    private String mSummery;


    public void setmImageUrl(String mImageUrl) {
        this.mImageUrl = mImageUrl;
    }

    public void setmCreator(String mCreator) {
        this.mCreator = mCreator;
    }

    private int mLikes;

    public ExampleItem(String imageUrl, String creator, int likes,String summery,String time ,String description) {
        mDescription = description;
        mTime = time;
        mSummery = summery;
        mImageUrl = imageUrl;
        mCreator = creator;
        mLikes = likes;
    }

    public String getmTime() {
        return mTime;
    }

    public String getmSummery() {
        return mSummery;
    }
    public String getImageUrl() {
        return mImageUrl;
    }

    public String getCreator() {
        return mCreator;
    }

    public int getLikeCount() {
        return mLikes;
    }

}
