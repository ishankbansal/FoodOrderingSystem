package model.order;

import model.common.Item;
import model.common.SelectionStrategyType;
import model.restaurant.Restaurant;

import java.util.Map;

public class Order {
    private static int idCounter = 0;

    private final int id;
    private final Map<Item, Integer> items;
    private final SelectionStrategyType strategyType;
    private OrderStatus status;
    private Restaurant assignedRestaurant;

    public Order(Map<Item, Integer> items, SelectionStrategyType strategyType) {
        this.id = ++idCounter;
        this.items = items;
        this.strategyType = strategyType;
        this.status = OrderStatus.CREATED;
    }

    public int getId() {
        return id;
    }

    public Map<Item, Integer> getItems() {
        return items;
    }

    public SelectionStrategyType getStrategyType() {
        return strategyType;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public Restaurant getAssignedRestaurant(){
        return assignedRestaurant;
    }

    public void assignRestaurant(Restaurant restaurant){
        this.assignedRestaurant = restaurant;
        this.status = OrderStatus.ACCEPTED;
    }

    public void markCompleted() {
        this.status = OrderStatus.COMPLETED;
    }
}
