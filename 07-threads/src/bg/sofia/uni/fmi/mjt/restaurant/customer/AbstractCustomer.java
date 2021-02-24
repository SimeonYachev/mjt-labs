package bg.sofia.uni.fmi.mjt.restaurant.customer;

import bg.sofia.uni.fmi.mjt.restaurant.Meal;
import bg.sofia.uni.fmi.mjt.restaurant.Order;
import bg.sofia.uni.fmi.mjt.restaurant.Restaurant;

import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractCustomer extends Thread {

    Restaurant restaurant;
    Meal meal;

    public AbstractCustomer(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        try {
            sleep(ThreadLocalRandom.current().nextInt(1, 6));
        } catch (InterruptedException e) {
            throw new RuntimeException("Customer thread sleep failed.");
        }
        this.meal = Meal.chooseFromMenu();
        restaurant.submitOrder(new Order(meal, this));
    }

    public abstract boolean hasVipCard();
}