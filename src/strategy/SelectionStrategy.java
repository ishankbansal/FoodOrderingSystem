package strategy;

import model.order.Order;
import model.restaurant.Restaurant;

import java.util.List;

public interface SelectionStrategy {
    Restaurant selectRestaurant(List<Restaurant> restaurantList, Order order);
}
