package com.alo.store.model;

/**
 * Элемент корзины с выбранным размером и количеством.
 */
public class CartItem {
    private final String productId;
    private String size;
    private int quantity;

    public CartItem(String productId, String size, int quantity) {
        this.productId = productId;
        this.size = size;
        this.quantity = quantity;
    }

    public String getProductId() {
        return productId;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}




