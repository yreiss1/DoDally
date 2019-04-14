package com.yuval.reiss.ourstory.Objects;

public class CharityObject {

    private String name;
    private String description;
    private String imageURL;
    private String thumbImageURL;

    public CharityObject(String name, String description, String thumbImageURL, String imageURL) {
        this.name = name;
        this.description = description;
        this.thumbImageURL = thumbImageURL;
        this.imageURL = imageURL;
    }

    public String getName() {
        return name;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getDescription() {
        return description;
    }

    public String getThumbImageURL() {
        return thumbImageURL;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
