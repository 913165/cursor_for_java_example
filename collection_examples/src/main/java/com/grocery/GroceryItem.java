package com.grocery;

/**
 * One line on the grocery list: name, how many, and the price of one unit.
 */
public record GroceryItem(String name, int quantity, double price) {

    public GroceryItem withAddedQuantity(int more) {
        return new GroceryItem(name, quantity + more, price);
    }

    public double lineTotal() {
        return quantity * price;
    }

    @Override
    public String toString() {
        return String.format("%-16s  qty %-4d  @ $%.2f   = $%.2f",
                name, quantity, price, lineTotal());
    }
}
