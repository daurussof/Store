package com.alo.store.model;

import java.util.List;

/**
 * Модель заказа с суммой и статусом.
 */
public class Order {
    private final String id;
    private final List<CartItem> items;
    private final double total;
    private final String status;
    private final String customerName;
    private final String address;
    private final String phone;

    public Order(String id,
                 List<CartItem> items,
                 double total,
                 String status,
                 String customerName,
                 String address,
                 String phone) {
        this.id = id;
        this.items = items;
        this.total = total;
        this.status = status;
        this.customerName = customerName;
        this.address = address;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public List<CartItem> getItems() {
        return items;
    }

    public double getTotal() {
        return total;
    }

    public String getStatus() {
        return status;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }
}




