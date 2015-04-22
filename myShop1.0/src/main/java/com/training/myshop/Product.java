package com.training.myshop;

public class Product {

    private int productId;
    private int modelId;
    private int categoryId;
    private int brandId;

    public int getProductId() {
        return this.productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;;
    }

    public int getCategoryId() {
        return this.categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public int getModelId() {
        return this.modelId;
    }

    public void setModelId(int modelId) {
        this.modelId = modelId;
    }

    public int getBrandId() {
        return this.brandId;
    }

    public void setBrandId(int brandId) {
        this.brandId = brandId;
    }

}
