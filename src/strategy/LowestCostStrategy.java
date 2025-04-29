package strategy;

import model.common.Item;
import model.order.Order;
import model.restaurant.MenuItem;
import model.restaurant.Restaurant;

import java.util.List;
import java.util.Map;

public class LowestCostStrategy implements SelectionStrategy{
    @Override
    public Restaurant selectRestaurant(List<Restaurant> restaurantList, Order order) {
        Restaurant selectedRestaurant = null;
        double lowestCost = Double.MAX_VALUE;

        for(Restaurant restaurant : restaurantList) {
            if(!restaurant.canAcceptMoreOrders()) continue;
            if(!restaurant.canProcessOrder(order.getItems())) continue;

            double totalCost = 0;

            for(Map.Entry<Item, Integer> entry : order.getItems().entrySet()) {
                MenuItem menuItem = restaurant.getMenu().get(entry.getKey());
                totalCost += menuItem.getPrice() * entry.getValue();
            }

            if(totalCost < lowestCost) {
                lowestCost = totalCost;
                selectedRestaurant = restaurant;
            }
        }

        return selectedRestaurant;
    }
}
