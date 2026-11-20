package com.workshop;

import java.util.Locale;

/**
 * A person checking in, identified by a username or badge.
 * Comparison ignores case, so "Ada" and "ada" are the same attendee.
 */
public record Attendee(String badge) {

    public Attendee {
        if (badge == null || badge.isBlank()) {
            throw new IllegalArgumentException("Badge cannot be blank.");
        }
        badge = badge.trim();
    }

    @Override
    public boolean equals(Object other) {
        return other instanceof Attendee attendee
                && badge.equalsIgnoreCase(attendee.badge);
    }

    @Override
    public int hashCode() {
        return badge.toLowerCase(Locale.ROOT).hashCode();
    }

    @Override
    public String toString() {
        return badge;
    }
}
