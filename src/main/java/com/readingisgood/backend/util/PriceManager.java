package com.readingisgood.backend.util;

import com.readingisgood.backend.entity.OrderItem;

import java.util.List;

public class PriceManager {

    public static double priceCalculate(List<OrderItem> orderItems) {
        double totalPrice = 0;
        for (OrderItem item : orderItems) {
            totalPrice += calculate(item.getQty(), item.getBook().getPrice());
        }
        return totalPrice;
    }

    public static double priceCalculate(OrderItem orderItem) {
        return calculate(orderItem.getQty(), orderItem.getBook().getPrice());
    }

    private static double calculate(int qty, double basePrice) {
        return qty * basePrice;
    }

}
