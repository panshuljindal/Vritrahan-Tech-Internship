package com.panshul.traveltriangle.Model;

public class ItemModel {
    String name;
    String price;
    String data;
    int image;

    public ItemModel(){

    }

    public ItemModel(String name, String price, String data, int image) {
        this.name = name;
        this.price = price;
        this.data = data;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }
}
