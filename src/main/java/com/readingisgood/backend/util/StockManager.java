package com.readingisgood.backend.util;

import com.readingisgood.backend.entity.Book;
import com.readingisgood.backend.entity.OrderItem;

import java.util.List;

public class StockManager {
    public static boolean stockStatus(OrderItem item) {
        return item.getQty() == item.getBook().getStock();
    }

    public static boolean stockStatus(List<OrderItem> orderItems) {
        for (OrderItem item : orderItems) {
            if (item.getQty() != item.getBook().getStock())
                return false;
        }
        return true;
    }

    public static Book updateStock(Book book, int qty) {
        book.setStock(book.getStock() - qty);
        return book;
    }
}
