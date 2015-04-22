package com.training.myshop;

public class Model {

    private int modelId;
    private int brandId;
    private int quantity;
    private String modelName;
    private String modelDescription;
    private float price;

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public String getModelName() {
        return this.modelName;
    }

    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }

    public String getModelDescription() {
        return this.modelDescription;
    }


    public void setPrice(float price) {
        this.price = price;
    }

    public float getPrice() {
        return this.price;
    }
}

