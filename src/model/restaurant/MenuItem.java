package model.restaurant;

import model.common.Item;

public class MenuItem {
    private final Item item;
    private double price;

    public MenuItem(Item item, double price) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if(price <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }
        this.item = item;
        this.price = price;
    }

    public Item getItem() {
        return item;
    }

    public double getPrice() {
        return price;
    }

    public void updatePrice(double price) {
        if(price <= 0){
            throw new IllegalArgumentException("Price must be positive");
        }
        this.price = price;
    }
}
