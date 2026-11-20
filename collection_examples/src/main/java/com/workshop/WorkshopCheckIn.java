package com.workshop;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

/**
 * Console check-in desk for a workshop. Duplicate badges stay out of the
 * attendance list and are counted as repeat check-ins.
 */
public class WorkshopCheckIn {
    private final AttendanceBook book = new AttendanceBook();
    private final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        new WorkshopCheckIn().run();
    }

    private void run() {
        System.out.println("=== Workshop Check-In ===");
        boolean open = true;
        while (open) {
            printMenu();
            switch (readLine("Choose an option: ")) {
                case "1" -> checkIn();
                case "2" -> verify();
                case "3" -> showAttendance();
                case "4" -> showRepeats();
                case "5" -> open = false;
                default -> System.out.println("Please choose 1, 2, 3, 4, or 5.");
            }
        }
        System.out.println("\n========== Check-in summary ==========");
        System.out.println("Unique attendees: " + book.uniqueCount());
        System.out.println("Repeat check-ins: " + book.repeatCheckInCount());
        System.out.println("People who repeated:");
        printSorted(book.repeatArrivals());
        System.out.println("======================================");
    }

    private void printMenu() {
        System.out.println();
        System.out.println("1. Check in");
        System.out.println("2. Verify an attendee");
        System.out.println("3. Show attendance");
        System.out.println("4. Show repeat arrivals");
        System.out.println("5. Close check-in and show summary");
    }

    private void checkIn() {
        Attendee attendee = readAttendee("Username or badge: ");
        if (attendee == null) {
            return;
        }
        if (book.checkIn(attendee)) {
            System.out.println(attendee + " is checked in.");
        } else {
            System.out.println(attendee + " was already checked in. Repeat ignored.");
        }
    }

    private void verify() {
        Attendee attendee = readAttendee("Username or badge to look up: ");
        if (attendee == null) {
            return;
        }
        if (book.hasCheckedIn(attendee)) {
            System.out.println(attendee + " is on the attendance list.");
        } else {
            System.out.println(attendee + " has not checked in.");
        }
    }

    private void showAttendance() {
        System.out.println("\nChecked in (" + book.uniqueCount() + "):");
        printSorted(book.attendees());
    }

    private void showRepeats() {
        System.out.println("\nTried to check in more than once:");
        printSorted(book.repeatArrivals());
        System.out.println("Repeat check-ins: " + book.repeatCheckInCount());
    }

    private void printSorted(HashSet<Attendee> attendees) {
        if (attendees.isEmpty()) {
            System.out.println("  (none)");
            return;
        }
        List<Attendee> ordered = new ArrayList<>(attendees);
        ordered.sort(Comparator.comparing(attendee -> attendee.badge().toLowerCase()));
        for (Attendee attendee : ordered) {
            System.out.println("  - " + attendee);
        }
    }

    private Attendee readAttendee(String prompt) {
        String badge = readLine(prompt);
        if (badge.isEmpty()) {
            System.out.println("Badge cannot be blank.");
            return null;
        }
        return new Attendee(badge);
    }

    private String readLine(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }
}
