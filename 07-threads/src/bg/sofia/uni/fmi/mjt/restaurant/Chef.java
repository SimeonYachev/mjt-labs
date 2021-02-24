package bg.sofia.uni.fmi.mjt.restaurant;

public class Chef extends Thread {

    private final Restaurant restaurant;
    private int mealsCount;

    public Chef(int id, Restaurant restaurant) {
        this.restaurant = restaurant;
    }

    @Override
    public void run() {
        while (restaurant.nextOrder() != null) {
            mealsCount++;
        }
    }

    /**
     * Returns the total number of meals that this chef has cooked.
     **/
    public int getTotalCookedMeals() {
        return mealsCount;
    }

}