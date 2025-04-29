package model.restaurant;

import model.common.Item;
import model.order.Order;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Restaurant {
    private final String name;
    private final double rating;
    private final int maxOrders;
    private final Map<Item, MenuItem> menu = new HashMap<>();
    private final Set<Order> currentOrders = new HashSet<>();

    public Restaurant(String name, double rating, int maxOrders) {
        if(name == null || name.isBlank()) {
            throw new IllegalArgumentException("Restaurant name cannot be empty");
        }

        if(rating < 0.0 || rating > 5.0) {
            throw new IllegalArgumentException("Rating must be between 0 and 5");
        }

        if(maxOrders <= 0){
            throw new IllegalArgumentException("Max orders must be positive");
        }
        this.name = name;
        this.rating = rating;
        this.maxOrders = maxOrders;
    }

    public String getName() {
        return name;
    }

    public double getRating() {
        return rating;
    }

    public Map<Item, MenuItem> getMenu() {
        return new HashMap<>(menu);
    }

    public void addItemToMenu(Item item, double price){
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if(menu.containsKey(item)) {
            throw new IllegalArgumentException("Item already exist in menu: " + item.getName());
        }
        menu.putIfAbsent(item, new MenuItem(item, price));
    }

    public void updateMenuItem(Item item, double newPrice) {
        if(item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        if(!menu.containsKey(item)) {
            throw new IllegalArgumentException("Item not found in menu: " + item.getName());
        }

        menu.get(item).updatePrice(newPrice);
    }

    public boolean canProcessOrder(Map<Item, Integer> orderItems) {
        if(orderItems == null) {
            return false;
        }

        for(Item item : orderItems.keySet()) {
            if(!menu.containsKey(item)) {
                return false;
            }
        }

        return true;
    }

    public boolean canAcceptMoreOrders() {
        return currentOrders.size() < maxOrders;
    }

    public void acceptOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        currentOrders.add(order);
    }

    public void completeOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }
        currentOrders.remove(order);
    }
}
