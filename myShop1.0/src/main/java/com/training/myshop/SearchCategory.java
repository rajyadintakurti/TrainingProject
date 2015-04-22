package com.training.myshop;

public class SearchCategory {
    public String categoryName,brandName,modelName,modelDescription;
    private float price;
    //private int categoryId;


    /*public int getCategoryId() {
        return this.categoryId;
    }
    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }*/
    public String getCategoryName() {
        return this.categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
    public String getBrandName() {
        return this.brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    } 
    public String getModelName() {
        return this.modelName;
    }
    public void setModelName(String modelName) {
        this.modelName = modelName;
    }
    public float getPrice() {
        return this.price;
    }
    public void setPrice(float price) {
        this.price = price;
    }
    public String getModelDescription() {
        return this.modelDescription;
    }
    public void setModelDescription(String modelDescription) {
        this.modelDescription = modelDescription;
    }
}

