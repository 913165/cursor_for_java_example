package com.cafe;

import java.util.Collection;
import java.util.HashMap;
import java.util.Locale;
import java.util.TreeMap;

/**
 * Sales totals. HashMap answers "how many of this code?" in one lookup.
 * TreeMap keeps the same items in alphabetical name order for the summary.
 */
public class SalesLedger {
    private final HashMap<String, MenuItem> itemsByCode = new HashMap<>();
    private final HashMap<String, Integer> quantityByCode = new HashMap<>();
    private final TreeMap<String, MenuItem> itemsByName = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);

    public void addItem(MenuItem item) {
        itemsByCode.put(item.code(), item);
        itemsByName.put(item.name(), item);
        quantityByCode.putIfAbsent(item.code(), 0);
    }

    public MenuItem findByCode(String code) {
        if (code == null) {
            return null;
        }
        return itemsByCode.get(code.trim().toUpperCase(Locale.ROOT));
    }

    /** Items in A-to-Z name order, from the TreeMap. */
    public Collection<MenuItem> itemsInNameOrder() {
        return itemsByName.values();
    }

    /**
     * Adds a sale. The running count is stored in the HashMap.
     *
     * @return false when the code is not on the menu
     */
    public boolean recordSale(String code, int count) {
        MenuItem item = findByCode(code);
        if (item == null || count <= 0) {
            return false;
        }
        quantityByCode.merge(item.code(), count, Integer::sum);
        return true;
    }

    public int quantityOf(String code) {
        MenuItem item = findByCode(code);
        if (item == null) {
            return 0;
        }
        return quantityByCode.getOrDefault(item.code(), 0);
    }

    public double revenueOf(String code) {
        MenuItem item = findByCode(code);
        if (item == null) {
            return 0;
        }
        return item.revenue(quantityOf(item.code()));
    }

    public int totalQuantity() {
        int total = 0;
        for (int quantity : quantityByCode.values()) {
            total += quantity;
        }
        return total;
    }

    public double salesTotal() {
        double total = 0;
        for (MenuItem item : itemsByName.values()) {
            total += item.revenue(quantityOf(item.code()));
        }
        return total;
    }
}
