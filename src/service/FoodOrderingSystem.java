package service;

import model.common.Item;
import model.common.SelectionStrategyType;
import model.order.Order;
import model.order.OrderStatus;
import model.restaurant.Restaurant;
import strategy.HighestRatingStrategy;
import strategy.LowestCostStrategy;
import strategy.SelectionStrategy;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class FoodOrderingSystem {
    private final List<Restaurant> restaurantList = new ArrayList<>();

    private static final Map<SelectionStrategyType, SelectionStrategy> strategies = Map.of(
            SelectionStrategyType.LOWEST_COST, new LowestCostStrategy(),
            SelectionStrategyType.HIGHEST_RATING, new HighestRatingStrategy()
    );

    public void onboardRestaurant(Restaurant restaurant) {
        if(restaurant == null) {
            throw new IllegalArgumentException("Restaurant cannot be null");
        }
        if(restaurantList.stream().anyMatch(r -> r.getName().equals(restaurant.getName()))) {
            throw new IllegalArgumentException("Restaurant with this name already exits");
        }
        restaurantList.add(restaurant);
    }

    public void updateMenu(String restaurantName, Item item, double price) {
        if(restaurantName == null || restaurantName.isBlank()) {
            throw new IllegalArgumentException("Restaurant cannot be empty");
        }

        Restaurant restaurant = findRestaurantByName(restaurantName)
                .orElseThrow(() -> new IllegalArgumentException("Restaurant not found: " + restaurantName));

        if(restaurant.getMenu().containsKey(item)) {
            restaurant.updateMenuItem(item, price);
        }
        else {
            restaurant.addItemToMenu(item, price);
        }
    }

    public void placeOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        SelectionStrategy selectionStrategy = strategies.get(order.getStrategyType());
        Restaurant selectedRestaurant = selectionStrategy.selectRestaurant(restaurantList, order);

        if(selectedRestaurant == null) {
            System.out.println("Order: " + order.getId() + " could not be assigned to any restaurant.");
            return;
        }

        selectedRestaurant.acceptOrder(order);
        order.assignRestaurant(selectedRestaurant);
        System.out.println("Order: " + order.getId() + " assigned to restaurant " + selectedRestaurant.getName());
    }

    public void completeOrder(Order order) {
        if(order == null) {
            throw new IllegalArgumentException("Order cannot be null");
        }

        if(order.getStatus() != OrderStatus.ACCEPTED) {
            System.out.println("Cannot complete order. Order not yet Accepted.");
            return;
        }

        order.markCompleted();
        order.getAssignedRestaurant().completeOrder(order);
        System.out.println("Order: " + order.getId() + " has been completed!");
    }

    private Optional<Restaurant> findRestaurantByName(String name) {
        return restaurantList.stream()
                .filter(r -> r.getName().equals(name))
                .findFirst();
    }
}
