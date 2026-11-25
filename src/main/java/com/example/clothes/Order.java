package com.example.clothes;

public class Order {

    private int id;
    private int userId;
    private ClothesModel clothes;
    private int quantity;
    private double price;
    private String state;

    public Order(int id, int userId, ClothesModel clothesId, int quantity, double price, String state) {
        this.id = id;
        this.userId = userId;
        this.clothes = clothesId;
        this.quantity = quantity;
        this.price = price;
        this.state = state;
    }

    public Order() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ClothesModel getClothes() {
        return clothes;
    }

    public void setClothes(ClothesModel clothesId) {
        this.clothes = clothesId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

//    @Override
//    public String toString() {
//        return "Cart{" +
//                "id=" + id +
//                ", userId=" + userId +
//                ", clothesId=" + clothesId +
//                ", quantity=" + quantity +
//                ", price=" + price +
//                ", state='" + state + '\'' +
//                '}';
//    }
}
