import model.common.Item;
import model.common.SelectionStrategyType;
import model.order.Order;
import model.restaurant.Restaurant;
import service.FoodOrderingSystem;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        FoodOrderingSystem system = new FoodOrderingSystem();

        // Create common food items
        Item biryani = new Item("Veg Biryani");
        Item chickenBiryani = new Item("Chicken Biryani");
        Item idli = new Item("Idli");
        Item dosa = new Item("Dosa");
        Item pizza = new Item("Pizza");
        Item burger = new Item("Burger");
        Item fries = new Item("Fries");
        Item pasta = new Item("Pasta");
        Item shake = new Item("Shake");
        Item chicken65 = new Item("Chicken 65");
        Item gobiManchurian = new Item("Gobi Manchurian");

        System.out.println("====== TEST CASE 1: Onboard Restaurants ======");
        try {
            // Valid restaurant onboarding
            Restaurant r1 = new Restaurant("Taj Biryani", 4.8, 3);
            r1.addItemToMenu(biryani, 120);
            r1.addItemToMenu(chickenBiryani, 180);
            system.onboardRestaurant(r1);

            Restaurant r2 = new Restaurant("Chennai Cafe", 4.2, 5);
            r2.addItemToMenu(idli, 15);
            r2.addItemToMenu(dosa, 50);
            r2.addItemToMenu(biryani, 100);
            system.onboardRestaurant(r2);

            Restaurant r3 = new Restaurant("Pizza Hut", 4.5, 2);
            r3.addItemToMenu(pizza, 200);
            r3.addItemToMenu(pasta, 180);
            r3.addItemToMenu(shake, 90);
            system.onboardRestaurant(r3);

            // Invalid restaurant (negative rating)
            Restaurant invalidRestaurant = new Restaurant("Bad Restaurant", -1.0, 5);
        } catch (IllegalArgumentException e) {
            System.err.println("Error creating restaurant: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 2: Menu Updates ======");
        try {
            // Valid menu updates
            system.updateMenu("Taj Biryani", chicken65, 220);  // Add new item
            system.updateMenu("Chennai Cafe", biryani, 110);  // Update price

            // Invalid update (non-existent item)
            system.updateMenu("Taj Biryan", new Item("Sushi"), 500);
        } catch (IllegalArgumentException e) {
            System.err.println("Menu update failed: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 3: Order Placement - Success Cases ======");
        // Order 1: Lowest cost strategy
        try {
            Map<Item, Integer> order1Items = new HashMap<>();
            order1Items.put(idli, 3);
            order1Items.put(dosa, 2);
            Order order1 = new Order(order1Items, SelectionStrategyType.LOWEST_COST);
            system.placeOrder(order1);  // Should go to Chennai Cafe (total: 3*15 + 2*50 = 145)
        } catch (Exception e) {
            System.err.println("Order failed: " + e.getMessage());
        }

        // Order 2: Highest rating strategy
        try {
            Map<Item, Integer> order2Items = new HashMap<>();
            order2Items.put(biryani, 2);
            Order order2 = new Order(order2Items, SelectionStrategyType.HIGHEST_RATING);
            system.placeOrder(order2);  // Should go to Taj Biryani (rating 4.8)
        } catch (Exception e) {
            System.err.println("Order failed: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 4: Capacity Limits ======");
        try {
            // Fill Pizza Hut's capacity
            Map<Item, Integer> order3Items = new HashMap<>();
            order3Items.put(pizza, 1);
            Order order3 = new Order(order3Items, SelectionStrategyType.LOWEST_COST);
            system.placeOrder(order3);  // Pizza Hut (capacity 2, now 1 free)

            Order order4 = new Order(order3Items, SelectionStrategyType.LOWEST_COST);
            system.placeOrder(order4);  // Pizza Hut (capacity 2, now 0 free)

            // Try to exceed capacity
            Order order5 = new Order(order3Items, SelectionStrategyType.LOWEST_COST);
            system.placeOrder(order5);  // Should fail
        } catch (Exception e) {
            System.err.println("Order failed: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 5: Order Completion ======");
        try {
            // Complete order2 to free capacity
            Map<Item, Integer> order2Items = new HashMap<>();
            order2Items.put(biryani, 2);
            Order order2 = new Order(order2Items, SelectionStrategyType.HIGHEST_RATING);
            system.placeOrder(order2);
            system.completeOrder(order2);  // Free up Taj Biryani's capacity

            // Place new order
            Order order6 = new Order(order2Items, SelectionStrategyType.HIGHEST_RATING);
            system.placeOrder(order6);  // Should succeed
        } catch (Exception e) {
            System.err.println("Order failed: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 6: Unfulfillable Orders ======");
        try {
            // Order with unavailable item
            Map<Item, Integer> invalidOrderItems = new HashMap<>();
            invalidOrderItems.put(new Item("Sushi"), 2);
            Order invalidOrder = new Order(invalidOrderItems, SelectionStrategyType.LOWEST_COST);
            system.placeOrder(invalidOrder);
        } catch (Exception e) {
            System.err.println("Order failed as expected: " + e.getMessage());
        }

        System.out.println("\n====== TEST CASE 7: Edge Cases ======");
        try {
            // Zero quantity order
            Map<Item, Integer> zeroQtyItems = new HashMap<>();
            zeroQtyItems.put(idli, 0);
            Order zeroOrder = new Order(zeroQtyItems, SelectionStrategyType.LOWEST_COST);
        } catch (IllegalArgumentException e) {
            System.err.println("Caught invalid order: " + e.getMessage());
        }

        // Duplicate restaurant onboarding
        try {
            Restaurant duplicate = new Restaurant("Taj Biryani", 4.0, 3);
            system.onboardRestaurant(duplicate);
        } catch (IllegalArgumentException e) {
            System.err.println("Duplicate prevention working: " + e.getMessage());
        }
    }
}