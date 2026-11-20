package com.grocery;

import java.util.Scanner;

/**
 * Interactive grocery list. Pick an item, enter quantity and price,
 * add or remove lines, then choose "I'm done" to see the sum total.
 */
public class GroceryListManager {
    private static final String[] CATALOG = {
            "Milk", "Bread", "Eggs", "Rice", "Apples",
            "Bananas", "Chicken", "Tomatoes", "Cheese", "Coffee"
    };

    private final GroceryList list = new GroceryList();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new GroceryListManager().run();
    }

    private void run() {
        System.out.println("=== Grocery List Manager ===");
        boolean shopping = true;
        while (shopping) {
            printMenu();
            int choice = readInt("Choose an option: ");
            switch (choice) {
                case 1 -> addItem();
                case 2 -> removeItem();
                case 3 -> {
                    System.out.println("\nYour list:");
                    list.printList();
                }
                case 4 -> shopping = false;
                default -> System.out.println("Please choose 1, 2, 3, or 4.");
            }
        }
        finish();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Add item");
        System.out.println("2. Remove item");
        System.out.println("3. View list");
        System.out.println("4. I'm done");
    }

    private void addItem() {
        System.out.println("\nSelect an item:");
        for (int i = 0; i < CATALOG.length; i++) {
            System.out.printf("  %d. %s%n", i + 1, CATALOG[i]);
        }
        System.out.printf("  %d. Something else (type the name)%n", CATALOG.length + 1);

        int pick = readInt("Item number: ");
        String name;
        if (pick >= 1 && pick <= CATALOG.length) {
            name = CATALOG[pick - 1];
        } else if (pick == CATALOG.length + 1) {
            name = readLine("Item name: ");
            if (name.isEmpty()) {
                System.out.println("Name cannot be blank. Nothing added.");
                return;
            }
        } else {
            System.out.println("That item number is not on the list.");
            return;
        }

        int quantity = readInt("Quantity: ");
        if (quantity <= 0) {
            System.out.println("Quantity must be at least 1. Nothing added.");
            return;
        }

        double price = readDouble("Price for one: ");
        if (price < 0) {
            System.out.println("Price cannot be negative. Nothing added.");
            return;
        }

        list.addItem(name, quantity, price);
        System.out.printf("Added %d x %s @ $%.2f%n", quantity, name, price);
    }

    private void removeItem() {
        if (list.isEmpty()) {
            System.out.println("Nothing to remove. The list is empty.");
            return;
        }
        System.out.println("\nWhich line do you want to remove?");
        list.printList();
        int pick = readInt("Line number (0 to cancel): ");
        if (pick == 0) {
            return;
        }
        if (list.removeItem(pick - 1)) {
            System.out.println("Removed.");
        } else {
            System.out.println("That line number is not on the list.");
        }
    }

    private void finish() {
        System.out.println("\n========== Your grocery list ==========");
        list.printList();
        System.out.println("======================================");
        System.out.printf("SUM TOTAL: $%.2f%n", list.sumTotal());
        System.out.println("======================================");
    }

    private int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a whole number.");
            }
        }
    }

    private double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("Please enter a number, for example 2.49");
            }
        }
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
