package com.yuval.reiss.ourstory.Objects;


public class TaskObject {

    private float amount;
    private String title;
    private String image;
    private String description;
    private long time;
    private String cause;
    private String uid;


    public TaskObject() {

    }

    public TaskObject(float amount, String title,String cause, String imageURL, String description, long time) {
        this.amount = amount;
        this.title = title;
        this.image = image;
        this.description = description;
        this.time = time;
        this.cause = cause;


    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public float getAmount() {
        return amount;
    }

    public long getTime() {
        return time;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }

    public String getCause() {
        return cause;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUid() {
        return uid;
    }
}

