package com.example.pizzeria.data.model;
import com.google.gson.annotations.SerializedName;
public class Pizza {
    private int id;
    private String name;
    private String details;
    private double price;
    private boolean isSelected;
    @SerializedName("image_url")
    private String imageUrl;

    // Gettery i settery
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

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "Pizza{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", details='" + details + '\'' +
                ", price=" + price +
                ", imageUrl='" + imageUrl + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true; // Sprawdzanie referencji
        if (o == null || getClass() != o.getClass()) return false; // Sprawdzanie klasy
        Pizza pizza = (Pizza) o;
        return id == pizza.id; // Porównywanie ID
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id); // Generowanie hash na podstawie ID
    }
}
