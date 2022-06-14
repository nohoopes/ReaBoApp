package id19110100.hcmute.edu.reaboadmin.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private int id;
    private String name;
    private int price;
    private int img;
    private String Restaurant;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getImg() {
        return img;
    }

    public void setImg(int img) {
        this.img = img;
    }

    public String getRestaurant() {
        return Restaurant;
    }

    public void setRestaurant(String Restaurant) {
        this.Restaurant = Restaurant;
    }

    public Product(int id, String name, int price, int img, String description) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.img = img;
        this.Restaurant = description;
    }
}

