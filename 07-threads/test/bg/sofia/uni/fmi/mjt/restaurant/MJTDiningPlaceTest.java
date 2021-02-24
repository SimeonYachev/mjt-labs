package bg.sofia.uni.fmi.mjt.restaurant;

import bg.sofia.uni.fmi.mjt.restaurant.customer.AbstractCustomer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.Customer;
import bg.sofia.uni.fmi.mjt.restaurant.customer.VipCustomer;
import org.junit.Test;

import static java.lang.Thread.sleep;
import static org.junit.Assert.assertEquals;

public class MJTDiningPlaceTest {

    @Test
    public void testGetOrdersCountStandard() {
        int chefCount = 3;
        MJTDiningPlace mjt = new MJTDiningPlace(chefCount);

        AbstractCustomer[] customers = new AbstractCustomer[15];
        for (int i = 0; i < 15; ++i) {
            if (i % 2 == 0) {
                customers[i] = new VipCustomer(mjt);
            } else {
                customers[i] = new Customer(mjt);
            }
            customers[i].start();
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        int expected = 15;

        assertEquals("Total orders count is incorrect.", expected, mjt.getOrdersCount());
    }

    @Test
    public void testChefCookedMealsCountStandard() {
        int chefCount = 3;
        MJTDiningPlace mjt = new MJTDiningPlace(chefCount);

        AbstractCustomer[] customers = new AbstractCustomer[15];
        for (int i = 0; i < 15; ++i) {
            if (i % 2 == 0) {
                customers[i] = new VipCustomer(mjt);
            } else {
                customers[i] = new Customer(mjt);
            }
            customers[i].start();
        }

        try {
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        mjt.close();

        int expected = 0;
        for (Chef c : mjt.getChefs()) {
            expected += c.getTotalCookedMeals();
        }

        assertEquals("Not all orders were done by the chefs.", expected, mjt.getOrdersCount());
    }
}
