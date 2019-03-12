package com.example.sindhu.pixabayimagesapp;

public class ItemModel {
    String likes,views,tags,mainImg;


    public String getLikes() {
        return likes;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public String getViews() {
        return views;
    }

    public void setViews(String views) {
        this.views = views;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getMainImg() {
        return mainImg;
    }

    public void setMainImg(String mainImg) {
        this.mainImg = mainImg;
    }

    public ItemModel(String likes, String views, String tags, String mainImg) {
        this.likes = likes;
        this.views = views;
        this.tags = tags;
        this.mainImg = mainImg;
    }
}

