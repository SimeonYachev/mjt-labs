package bg.sofia.uni.fmi.mjt.restaurant;

import java.util.PriorityQueue;
import java.util.Queue;

public class MJTDiningPlace implements Restaurant {


    private int ordersCount = 0;
    private Queue<Order> orders;
    private Queue<Order> vipOrders;
    private Chef[] chefs;
    private boolean closed;

    public MJTDiningPlace(int chefCount) {
        int initCapacity = 11;
        orders = new PriorityQueue<Order>(initCapacity, (Order o1, Order o2) ->
                o2.meal().getCookingTime() - o1.meal().getCookingTime());
        vipOrders = new PriorityQueue<Order>(initCapacity, (Order o1, Order o2) ->
                o2.meal().getCookingTime() - o1.meal().getCookingTime());
        closed = false;
        chefs = new Chef[chefCount];
        for (int i = 0; i < chefCount; ++i) {
            chefs[i] = new Chef(i, this);
            chefs[i].start();
        }
    }

    @Override
    public synchronized void submitOrder(Order order) {
        if (order.customer().hasVipCard()) {
            vipOrders.add(order);
        } else {
            orders.add(order);
        }
        notifyAll();
        ordersCount++;
    }

    @Override
    public synchronized Order nextOrder() {
        if (closed && vipOrders.isEmpty() && orders.isEmpty()) {
            return null;
        }

        if (vipOrders.isEmpty() && orders.isEmpty()) {
            try {
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException("Chefs wait failed.");
            }
        }

        if (!vipOrders.isEmpty()) {
            return vipOrders.poll();
        } else {
            return orders.poll();
        }
    }

    @Override
    public int getOrdersCount() {
        return ordersCount;
    }

    @Override
    public Chef[] getChefs() {
        return chefs;
    }

    @Override
    public void close() {
        closed = true;

        synchronized (this) {
            this.notifyAll();
        }
    }
}
