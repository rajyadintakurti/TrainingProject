package com.myshop.www;

public class Product {

    private int pid, qty;
    private String pname, pcode;
    private double price;

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getPid() {
        return this.pid;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public int getQty() {
        return this.qty;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPname() {
        return this.pname;
    }

    public void setPcode(String pcode) {
        this.pcode = pcode;
    }

    public String getPcode() {
        return this.pcode;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getPrice() {
        return this.price;
    }
}
