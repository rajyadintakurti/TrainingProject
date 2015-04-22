package com.training.myshop;

public class Image {

    private String imageId;
    private int modelId;
    private String imageDescription;

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public String getImageId() {
        return this.imageId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setImageDescription(String imageDescription) {
        this.imageDescription = imageDescription;
    }

    public String getImageDescription() {
        return this.imageDescription;
    }
}
