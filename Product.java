package com.alo.store.model;

import java.util.List;

/**
 * Модель товара. Хранит атрибуты карточки каталога и деталей.
 */
public class Product {
    private final String id;
    private final String name;
    private final String category;
    private final String description;
    private final double price;
    private final List<String> imageUrls;
    private final List<String> sizes;
    private final String color;
    private final String brand;

    public Product(String id,
                   String name,
                   String category,
                   String description,
                   double price,
                   List<String> imageUrls,
                   List<String> sizes,
                   String color,
                   String brand) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.description = description;
        this.price = price;
        this.imageUrls = imageUrls;
        this.sizes = sizes;
        this.color = color;
        this.brand = brand;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public double getPrice() {
        return price;
    }

    public List<String> getImageUrls() {
        return imageUrls;
    }

    public List<String> getSizes() {
        return sizes;
    }

    public String getColor() {
        return color;
    }

    public String getBrand() {
        return brand;
    }
}




