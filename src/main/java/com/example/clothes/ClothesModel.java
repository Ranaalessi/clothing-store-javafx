package com.example.clothes;

import static javafx.application.Application.launch;

public class ClothesModel {
    private int id;
    private String name;
    private String about;
    private String imgUrl;
    private double price;
    private int categoryId;

    public ClothesModel(int id, String name, String about, String imgUrl, double price, int categoryId) {
        this.id = id;
        this.name = name;
        this.about = about;
        this.imgUrl = imgUrl;
        this.price = price;
        this.categoryId = categoryId;
    }

    public ClothesModel() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    @Override
    public String toString() {
        return "ClothesModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", imgUrl='" + imgUrl + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                '}';
    }

}