package com.grocery;

import java.util.ArrayList;

/**
 * The shopping list itself. Items live in an ArrayList.
 */
public class GroceryList {
    private final ArrayList<GroceryItem> items = new ArrayList<>();

    public void addItem(String name, int quantity, double price) {
        for (int i = 0; i < items.size(); i++) {
            GroceryItem item = items.get(i);
            if (item.name().equalsIgnoreCase(name) && item.price() == price) {
                items.set(i, item.withAddedQuantity(quantity));
                return;
            }
        }
        items.add(new GroceryItem(name, quantity, price));
    }

    public boolean removeItem(int index) {
        if (index < 0 || index >= items.size()) {
            return false;
        }
        items.remove(index);
        return true;
    }

    public boolean isEmpty() {
        return items.isEmpty();
    }

    public int size() {
        return items.size();
    }

    public GroceryItem get(int index) {
        return items.get(index);
    }

    public double sumTotal() {
        double total = 0;
        for (GroceryItem item : items) {
            total += item.lineTotal();
        }
        return total;
    }

    public void printList() {
        if (items.isEmpty()) {
            System.out.println("  (your list is empty)");
            return;
        }
        for (int i = 0; i < items.size(); i++) {
            System.out.printf("  %d. %s%n", i + 1, items.get(i));
        }
        System.out.printf("  ---------------------------------------------%n");
        System.out.printf("  Running total: $%.2f%n", sumTotal());
    }
}
