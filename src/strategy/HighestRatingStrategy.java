package strategy;

import model.order.Order;
import model.restaurant.Restaurant;

import java.util.List;

public class HighestRatingStrategy implements SelectionStrategy{

    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurantList, Order order) {
        Restaurant selectedRestaurant = null;
        double highestRating = -1;

        for(Restaurant restaurant : restaurantList) {
            if(!restaurant.canAcceptMoreOrders()) continue;
            if(!restaurant.canProcessOrder(order.getItems())) continue;

            if(restaurant.getRating() > highestRating) {
                highestRating = restaurant.getRating();
                selectedRestaurant = restaurant;
            }
        }

        return selectedRestaurant;
    }
}
