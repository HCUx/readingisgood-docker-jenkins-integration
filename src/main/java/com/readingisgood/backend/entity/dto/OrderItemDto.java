package com.readingisgood.backend.entity.dto;

public class OrderItemDto {
    private long id;

    private BookDto book;

    private int qty;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BookDto getBook() {
        return book;
    }

    public void setBook(BookDto book) {
        this.book = book;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }
}
