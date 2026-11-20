package com.cafe;

import java.util.Scanner;

/**
 * Cafe register. Record a code and a count, look up one item's running total,
 * then print every item in alphabetical order with the sales total.
 */
public class CafeRegister {
    private final SalesLedger ledger = new SalesLedger();
    private final Scanner scanner = new Scanner(System.in);

    public CafeRegister() {
        ledger.addItem(new MenuItem("ESP", "Espresso", 3.00));
        ledger.addItem(new MenuItem("LAT", "Latte", 4.50));
        ledger.addItem(new MenuItem("CAP", "Cappuccino", 4.25));
        ledger.addItem(new MenuItem("TEA", "Tea", 2.75));
        ledger.addItem(new MenuItem("MUF", "Muffin", 3.25));
    }

    public static void main(String[] args) {
        new CafeRegister().run();
    }

    private void run() {
        System.out.println("=== Cafe Sales Tracker ===");
        boolean open = true;
        while (open) {
            printMenu();
            switch (readLine("Choose an option: ")) {
                case "1" -> recordSale();
                case "2" -> lookUp();
                case "3" -> printSummary();
                case "4" -> open = false;
                default -> System.out.println("Please choose 1, 2, 3, or 4.");
            }
        }
        System.out.println();
        printSummary();
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Record a sale");
        System.out.println("2. Look up an item total");
        System.out.println("3. Print summary (A to Z)");
        System.out.println("4. I'm done");
    }

    private void recordSale() {
        System.out.println("\nMenu (alphabetical):");
        printItemLines();
        String code = readLine("Item code: ");
        MenuItem item = ledger.findByCode(code);
        if (item == null) {
            System.out.println("That code is not on the menu.");
            return;
        }
        int count = readInt("Count: ");
        if (count <= 0) {
            System.out.println("Count must be at least 1. Nothing recorded.");
            return;
        }
        ledger.recordSale(item.code(), count);
        System.out.printf("Recorded %d x %s. Running total: %d ($%.2f)%n",
                count, item.name(), ledger.quantityOf(item.code()), ledger.revenueOf(item.code()));
    }

    private void lookUp() {
        String code = readLine("Item code: ");
        MenuItem item = ledger.findByCode(code);
        if (item == null) {
            System.out.println("That code is not on the menu.");
            return;
        }
        System.out.printf("%s (%s)  sold %-4d  revenue $%.2f%n",
                item.name(), item.code(), ledger.quantityOf(item.code()), ledger.revenueOf(item.code()));
    }

    private void printSummary() {
        System.out.println("\n========== Sales summary (A to Z) ==========");
        System.out.printf("%-6s %-14s %8s %6s %10s%n", "Code", "Item", "Price", "Sold", "Revenue");
        printItemLines();
        System.out.println("---------------------------------------------");
        System.out.printf("Items sold: %d%n", ledger.totalQuantity());
        System.out.printf("SALES TOTAL: $%.2f%n", ledger.salesTotal());
        System.out.println("=============================================");
    }

    private void printItemLines() {
        for (MenuItem item : ledger.itemsInNameOrder()) {
            int sold = ledger.quantityOf(item.code());
            System.out.printf("%-6s %-14s %8.2f %6d %10.2f%n",
                    item.code(), item.name(), item.price(), sold, item.revenue(sold));
        }
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

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
