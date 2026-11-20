package com.cafe;

import java.util.Locale;

/**
 * One drink or retail item: a short code, a display name, and the price of one.
 */
public record MenuItem(String code, String name, double price) {

    public MenuItem {
        if (code == null || code.isBlank()) {
            throw new IllegalArgumentException("Code cannot be blank.");
        }
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Name cannot be blank.");
        }
        if (price < 0) {
            throw new IllegalArgumentException("Price cannot be negative.");
        }
        code = code.trim().toUpperCase(Locale.ROOT);
        name = name.trim();
    }

    public double revenue(int quantity) {
        return price * quantity;
    }
}
